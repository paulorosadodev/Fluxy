package br.com.project.repository;

import br.com.project.model.ProductSupplier;
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
        String sql = "INSERT INTO entrega (fk_fornecedor_id, fk_produto_id, qnt_fornecida, valor_pago, data_reposicao) " +
                "VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                productSupplier.getSupplierId(),
                productSupplier.getProductId(),
                productSupplier.getProductAmount(),
                productSupplier.getPrice(),
                productSupplier.getDate()
        );
    }

    public List<ProductSupplier> findAll() {
        String sql = "SELECT * FROM entrega";
        return jdbcTemplate.query(sql, new ProductSupplierRowMapper());
    }

    public Optional<ProductSupplier> findBySupplierAndProduct(Integer fornecedorId, Integer produtoId) {
        String sql = "SELECT * FROM entrega WHERE fk_fornecedor_id = ? AND fk_produto_id = ?";
        List<ProductSupplier> result = jdbcTemplate.query(sql, new ProductSupplierRowMapper(), fornecedorId, produtoId);
        if (result.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(result.get(0));
    }

    public void update(ProductSupplier productSupplier) {
        String sql = "UPDATE entrega SET qnt_fornecida = ?, valor_pago = ?, data_reposicao = ? " +
                "WHERE fk_fornecedor_id = ? AND fk_produto_id = ?";
        int rows = jdbcTemplate.update(sql,
                productSupplier.getProductAmount(),
                productSupplier.getPrice(),
                productSupplier.getDate(),
                productSupplier.getSupplierId(),
                productSupplier.getProductId()
        );
        if (rows == 0) {
            throw new RuntimeException("Nenhum entrega atualizado. IDs: fornecedor " +
                    productSupplier.getSupplierId() + ", produto " + productSupplier.getProductId());
        }
    }

    public void deleteById(Integer fornecedorId, Integer produtoId) {
        String sql = "DELETE FROM entrega WHERE fk_fornecedor_id = ? AND fk_produto_id = ?";
        int rows = jdbcTemplate.update(sql, fornecedorId, produtoId);
        if (rows == 0) {
            throw new RuntimeException("Nenhuma entrega foi deletada. Fornecedor ID: " + fornecedorId + ", Produto ID: " + produtoId);
        }
    }

    private static class ProductSupplierRowMapper implements RowMapper<ProductSupplier> {
        @Override
        public ProductSupplier mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new ProductSupplier(
                    rs.getInt("fk_fornecedor_id"),
                    rs.getInt("fk_produto_id"),
                    rs.getInt("qnt_fornecida"),
                    rs.getBigDecimal("valor_pago"),
                    rs.getDate("data_reposicao").toLocalDate()
            );
        }
    }
}
