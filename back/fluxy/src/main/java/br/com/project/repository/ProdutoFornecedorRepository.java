package br.com.project.repository;

import br.com.project.model.ProdutoFornecedor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ProdutoFornecedorRepository {

    private final JdbcTemplate jdbcTemplate;

    public ProdutoFornecedorRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void save(ProdutoFornecedor produtoFornecedor) {
        String sql = "INSERT INTO produto_fornecedor (fk_fornecedor_id, fk_produto_id) VALUES (?, ?)";
        jdbcTemplate.update(sql,
                produtoFornecedor.getFkFornecedorId(),
                produtoFornecedor.getFkProdutoId()
        );
    }

    public List<ProdutoFornecedor> findAll() {
        String sql = "SELECT * FROM produto_fornecedor";
        return jdbcTemplate.query(sql, new ProdutoFornecedorRowMapper());
    }

    public void deleteByIds(Integer fornecedorId, Integer produtoId) {
        String sql = "DELETE FROM produto_fornecedor WHERE fk_fornecedor_id = ? AND fk_produto_id = ?";
        jdbcTemplate.update(sql, fornecedorId, produtoId);
    }

    private static class ProdutoFornecedorRowMapper implements RowMapper<ProdutoFornecedor> {
        @Override
        public ProdutoFornecedor mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new ProdutoFornecedor(
                    rs.getInt("fk_fornecedor_id"),
                    rs.getInt("fk_produto_id")
            );
        }
    }
}
