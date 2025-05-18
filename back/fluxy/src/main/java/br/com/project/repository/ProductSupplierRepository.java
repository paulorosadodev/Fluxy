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
        String sql = "INSERT INTO entrega (fk_fornecedor_id, fk_produto_id, qnt_fornecida, valor_pago, data_reposicao) VALUES (?, ?, ?, ?, ?)";
        try {
            jdbcTemplate.update(sql,
                    productSupplier.getSupplier(),
                    productSupplier.getProduct(),
                    productSupplier.getProductAmount(),
                    productSupplier.getPrice(),
                    productSupplier.getDate()
            );
        } catch (Exception e) {
            throw new RuntimeException("Erro ao salvar o registro: " + e.getMessage(), e);
        }
    }

    public List<ProductSupplier> findAll() {
        try {
            return jdbcTemplate.query("SELECT * FROM entrega", new ProductSupplierRowMapper());
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar todos os registros: " + e.getMessage(), e);
        }
    }

    public Optional<ProductSupplier> findBySupplierAndProduct(Integer fornecedorId, Integer produtoId) {
        String sql = "SELECT * FROM entrega WHERE fk_fornecedor_id = ? AND fk_produto_id = ?";
        try {
            return jdbcTemplate.query(sql, new ProductSupplierRowMapper(), fornecedorId, produtoId).stream().findFirst();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar o registro: " + e.getMessage(), e);
        }
    }

    public void update(ProductSupplier productSupplier) {
        String sql = "UPDATE entrega SET qnt_fornecida = ?, valor_pago = ?, data_reposicao = ? WHERE fk_fornecedor_id = ? AND fk_produto_id = ?";
        try {
            int rows = jdbcTemplate.update(sql,
                    productSupplier.getProductAmount(),
                    productSupplier.getPrice(),
                    productSupplier.getDate(),
                    productSupplier.getSupplier(),
                    productSupplier.getProduct()
            );
            if (rows == 0) {
                throw new RuntimeException("Nenhum registro atualizado.");
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao atualizar o registro: " + e.getMessage(), e);
        }
    }

    public void deleteById(Integer fornecedorId, Integer produtoId) {
        String sql = "DELETE FROM entrega WHERE fk_fornecedor_id = ? AND fk_produto_id = ?";
        try {
            int rows = jdbcTemplate.update(sql, fornecedorId, produtoId);
            if (rows == 0) {
                throw new RuntimeException("Nenhuma entrega foi deletada.");
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao deletar o registro: " + e.getMessage(), e);
        }
    }

    private static class ProductSupplierRowMapper implements RowMapper<ProductSupplier> {
        @Override
        public ProductSupplier mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new ProductSupplier(
                    rs.getInt("id"),
                    rs.getInt("fk_fornecedor_id"),
                    rs.getInt("fk_produto_id"),
                    rs.getInt("qnt_fornecida"),
                    rs.getBigDecimal("valor_pago"),
                    rs.getDate("data_reposicao").toLocalDate()
            );
        }
    }
}
