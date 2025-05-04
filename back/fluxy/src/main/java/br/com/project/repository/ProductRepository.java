package br.com.project.repository;

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
            throw new RuntimeException("Erro ao salvar produto: " + e.getMessage());
        }
    }

    public Optional<Product> findById(Integer id) {
        try {
            String sql = "SELECT * FROM produto WHERE id_produto = ?";
            List<Product> result = jdbcTemplate.query(sql, new ProductRowMapper(), id);
            return result.stream().findFirst();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar produto: " + e.getMessage());
        }
    }

    public List<Product> findAll() {
        try {
            String sql = "SELECT * FROM produto";
            return jdbcTemplate.query(sql, new ProductRowMapper());
        } catch (Exception e) {
            throw new RuntimeException("Erro ao listar produtos: " + e.getMessage());
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
            throw new RuntimeException("Erro ao atualizar produto: " + e.getMessage());
        }
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
            throw new RuntimeException("Erro ao buscar produto por código EA: " + e.getMessage());
        }
    }

    public void deleteById(Integer id) {
        try {
            String sql = "DELETE FROM produto WHERE id_produto = ?";
            jdbcTemplate.update(sql, id);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao deletar produto: " + e.getMessage());
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
