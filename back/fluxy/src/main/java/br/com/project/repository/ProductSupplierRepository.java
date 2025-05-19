package br.com.project.repository;

import br.com.project.model.ProductSupplier;
import br.com.project.service.ProductSupplierService;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class ProductSupplierRepository {

    private final JdbcTemplate jdbcTemplate;

    public ProductSupplierRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void save(ProductSupplier productSupplier) {
        String sql = "INSERT INTO entrega (fk_fornecedor_id, fk_produto_id, qnt_fornecida, valor_pago, data_reposicao) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                productSupplier.getSupplier(),
                productSupplier.getProduct(),
                productSupplier.getProductAmount(),
                productSupplier.getPrice(),
                productSupplier.getDate()
        );
    }

    public List<ProductSupplier> findAll() {
        return jdbcTemplate.query("SELECT * FROM entrega", new ProductSupplierRowMapper());
    }

    public Optional<ProductSupplier> findById(Integer entregaId) {
        String sql = "SELECT * FROM entrega WHERE id_entrega = ?";
        return jdbcTemplate.query(sql, new ProductSupplierRowMapper(), entregaId).stream().findFirst();
    }

    public void update(ProductSupplier productSupplier) {
        String sql = "UPDATE entrega SET fk_fornecedor_id = ?, fk_produto_id = ?, qnt_fornecida = ?, valor_pago = ?, data_reposicao = ? WHERE id_entrega = ?";
        jdbcTemplate.update(sql,
                productSupplier.getSupplier(),
                productSupplier.getProduct(),
                productSupplier.getProductAmount(),
                productSupplier.getPrice(),
                productSupplier.getDate(),
                productSupplier.getId()
        );
    }

    public void deleteById(Integer entregaId) {
        String sql = "DELETE FROM entrega WHERE id_entrega = ?";
        jdbcTemplate.update(sql, entregaId);
    }

    public void increaseStock(Integer productId, Integer quantity) {
        String updateStockSql = "UPDATE produto SET qtd_estoque = qtd_estoque + ? WHERE id_produto = ?";
        jdbcTemplate.update(updateStockSql, quantity, productId);
    }

    public void decreaseStock(Integer productId, Integer quantity) {
        String sql = "UPDATE produto SET qtd_estoque = qtd_estoque - ? WHERE id_produto = ?";
        jdbcTemplate.update(sql, quantity, productId);
    }

    public Integer findStockBySupplyId  (Integer entregaId) {
        String sql = "SELECT qnt_fornecida FROM entrega WHERE id_entrega = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, entregaId);
    }

    public Integer findProductIdByEntregaId(Integer entregaId) {
        String sql = "SELECT fk_produto_id FROM entrega WHERE id_entrega = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, entregaId);
    }

    private static class ProductSupplierRowMapper implements RowMapper<ProductSupplier> {
        @Override
        public ProductSupplier mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new ProductSupplier(
                    rs.getInt("id_entrega"),
                    rs.getInt("fk_fornecedor_id"),
                    rs.getInt("fk_produto_id"),
                    rs.getInt("qnt_fornecida"),
                    rs.getBigDecimal("valor_pago"),
                    rs.getDate("data_reposicao").toLocalDate()
            );
        }
    }
}

