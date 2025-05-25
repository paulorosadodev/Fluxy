package br.com.project.repository;

import br.com.project.dto.response.*;
import br.com.project.model.Product;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

@Repository
public class ProductRepository {

    private final JdbcTemplate jdbcTemplate;

    public ProductRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Integer save(Product product) {
        try {
            String sql = "INSERT INTO produto (codigo_categoria, qtd_estoque, cod_ea, preco, nome) VALUES (?, ?, ?, ?, ?)";
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, product.getCategoryCode());
                ps.setInt(2, product.getStockQuantity());
                ps.setString(3, product.getCodEa());
                ps.setDouble(4, product.getPrice());
                ps.setString(5, product.getName());
                return ps;
            }, keyHolder);

            return keyHolder.getKey().intValue();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public List<PriceHistoryResponseDTO> findHistoricoPreco(Integer produtoId) {
        String sql = """
            SELECT preco, data FROM historico_preco_produto WHERE codigo_produto = ? AND TIME(data) not like '00:00:00' ORDER BY data ASC
        """;

        return jdbcTemplate.query(sql, (rs, rowNum) ->
                new PriceHistoryResponseDTO(
                        rs.getDouble("preco"),
                        rs.getDate("data").toLocalDate()
                ), produtoId
        );
    }

    public Optional<Product> findById(Integer id) {
        try {
            String sql = "SELECT * FROM produto WHERE id_produto = ?";
            List<Product> result = jdbcTemplate.query(sql, new ProductRowMapper(), id);
            return result.stream().findFirst();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public List<Product> findAll() {
        try {
            String sql = "SELECT * FROM produto";
            return jdbcTemplate.query(sql, new ProductRowMapper());
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public void update(Product product) {
        try {
            String sql = "UPDATE produto SET codigo_categoria = ?, qtd_estoque = ?, cod_ea = ?, preco = ?, nome = ? WHERE id_produto = ?";
            jdbcTemplate.update(sql,
                    product.getCategoryCode(),
                    product.getStockQuantity(),
                    product.getCodEa(),
                    product.getPrice(),
                    product.getName(),
                    product.getIdProduct()
            );
        } catch (Exception e) {
            throw new RuntimeException("Erro ao atualizar produto", e);
        }
    }

    public List<ProductPurchaseCountResponseDTO> getMostPurchasedProducts() {
        String sql = """
        SELECT p.nome, SUM(c.qtd_produto) AS total_comprado
        FROM compra c
        JOIN produto p ON c.fk_produto_id = p.id_produto
        GROUP BY p.nome
        ORDER BY total_comprado DESC
        LIMIT 5
    """;

        return jdbcTemplate.query(sql, (rs, rowNum) ->
                new ProductPurchaseCountResponseDTO(
                        rs.getString("nome"),
                        rs.getInt("total_comprado")
                )
        );
    }

    public List<ProductPurchaseCountResponseDTO> getLeastPurchasedProducts() {
        String sql = """
        SELECT p.nome, COALESCE(SUM(c.qtd_produto), 0) AS total_comprado
        FROM produto p
        LEFT JOIN compra c ON c.fk_produto_id = p.id_produto
        GROUP BY p.nome
        ORDER BY total_comprado ASC
        LIMIT 5
    """;

        return jdbcTemplate.query(sql, (rs, rowNum) ->
                new ProductPurchaseCountResponseDTO(
                        rs.getString("nome"),
                        rs.getInt("total_comprado")
                )
        );
    }

    public int getTotalStockQuantity() {
        try {
            String sql = "SELECT COALESCE(SUM(qtd_estoque), 0) FROM produto";
            return jdbcTemplate.queryForObject(sql, Integer.class);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public int getTotalProductsCount() {
        try {
            String sql = "SELECT COUNT(*) FROM produto";
            return jdbcTemplate.queryForObject(sql, Integer.class);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public double getAveragePrice() {
        try {
            String sql = "SELECT AVG(produto.preco) FROM produto";
            return jdbcTemplate.queryForObject(sql, Double.class);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public double getTotalPrice() {
        try {
            String sql = "SELECT SUM(produto.preco * produto.qtd_estoque) FROM produto";
            return jdbcTemplate.queryForObject(sql, Double.class);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public List<CategoryProductCountDTO> getProductsCountByCategory() {
        String sql = "SELECT codigo_categoria, COUNT(*) AS quantidade FROM produto GROUP BY codigo_categoria";

        return jdbcTemplate.query(sql, (rs, rowNum) ->
                new CategoryProductCountDTO(
                        rs.getString("codigo_categoria"),
                        rs.getInt("quantidade")
                )
        );
    }

    public List<TopTierProductDTO> getMostExpensiveProducts() {
        String sql = "SELECT nome, preco FROM produto ORDER BY preco DESC LIMIT 5";

        return jdbcTemplate.query(sql, (rs, rowNum) ->
                new TopTierProductDTO(
                        rs.getString("nome"),
                        rs.getDouble("preco")
                )
        );
    }

    public List<TopTierProductDTO> getLeastExpensiveProducts() {
        String sql = "SELECT nome, preco FROM produto ORDER BY preco LIMIT 5";

        return jdbcTemplate.query(sql, (rs, rowNum) ->
                new TopTierProductDTO(
                        rs.getString("nome"),
                        rs.getDouble("preco")
                )
        );
    }

    public List<LowStockProductDTO> getLowStockProducts() {
        String sql = "SELECT nome, qtd_estoque FROM produto WHERE qtd_estoque <= 10 ";

        return jdbcTemplate.query(sql, (rs, rowNum) ->
                new LowStockProductDTO(
                        rs.getString("nome"),
                        rs.getInt("qtd_estoque")
                )
        );
    }

    public Product findByCodEa(String codEa) {
        try {
            String sql = "SELECT * FROM produto WHERE cod_ea = ?";
            return jdbcTemplate.queryForObject(sql, new Object[]{codEa}, (rs, rowNum) -> {
                Product product = new Product();
                product.setIdProduct(rs.getInt("id_produto"));
                product.setCodEa(rs.getString("cod_ea"));
                product.setName(rs.getString("nome"));
                product.setStockQuantity(rs.getInt("qtd_estoque"));
                product.setPrice(rs.getDouble("preco"));
                product.setCategoryCode(rs.getString("codigo_categoria"));
                return product;
            });
        } catch (EmptyResultDataAccessException e) {
            return null;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public void deleteById(Integer id) {
        try {
            String sql = "DELETE FROM produto WHERE id_produto = ?";
            jdbcTemplate.update(sql, id);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private static class ProductRowMapper implements RowMapper<Product> {
        @Override
        public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Product(
                    rs.getInt("id_produto"),
                    rs.getString("codigo_categoria"),
                    rs.getInt("qtd_estoque"),
                    rs.getString("cod_ea"),
                    rs.getDouble("preco"),
                    rs.getString("nome")
            );
        }
    }
}
