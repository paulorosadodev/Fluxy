package br.com.project.repository;

import br.com.project.model.ProductSupplier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ProductSupplierRepository {

    private final JdbcTemplate jdbcTemplate;

    public ProductSupplierRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void save(ProductSupplier productSupplier) {
        String sql = "INSERT INTO produto_fornecedor (fk_fornecedor_id, fk_produto_id) VALUES (?, ?)";
        jdbcTemplate.update(sql,
                productSupplier.getSupplierId(),
                productSupplier.getProductId()
        );
    }

    public void deleteByProductId(Integer productId) {
        String sql = "DELETE FROM produto_fornecedor WHERE fk_produto_id = ?";
        jdbcTemplate.update(sql, productId);
    }

    public void deleteBySupplierAndProduct(Integer supplierId, Integer productId) {
        String sql = "DELETE FROM produto_fornecedor WHERE fk_fornecedor_id = ? AND fk_produto_id = ?";
        jdbcTemplate.update(sql, supplierId, productId);
    }

    public List<ProductSupplier> findAll() {
        String sql = "SELECT * FROM produto_fornecedor";
        return jdbcTemplate.query(sql, new ProductSupplierRowMapper());
    }

    private static class ProductSupplierRowMapper implements RowMapper<ProductSupplier> {
        @Override
        public ProductSupplier mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new ProductSupplier(
                    rs.getInt("fk_fornecedor_id"),
                    rs.getInt("fk_produto_id")
            );
        }
    }
}
