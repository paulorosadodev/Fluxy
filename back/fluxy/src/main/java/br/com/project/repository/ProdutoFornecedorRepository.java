package br.com.project.repository;

import br.com.project.model.ProdutoFornecedor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

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
                produtoFornecedor.getFkProdutoId());
    }

    public Optional<ProdutoFornecedor> findByIds(Integer fornecedorId, Integer produtoId) {
        String sql = "SELECT * FROM produto_fornecedor WHERE fk_fornecedor_id = ? AND fk_produto_id = ?";
        List<ProdutoFornecedor> result = jdbcTemplate.query(sql, new ProdutoFornecedorRowMapper(), fornecedorId, produtoId);
        return result.stream().findFirst();
    }

    public List<ProdutoFornecedor> findAll() {
        String sql = "SELECT * FROM produto_fornecedor";
        return jdbcTemplate.query(sql, new ProdutoFornecedorRowMapper());
    }

    public void delete(Integer fornecedorId, Integer produtoId) {
        String sql = "DELETE FROM produto_fornecedor WHERE fk_fornecedor_id = ? AND fk_produto_id = ?";
        jdbcTemplate.update(sql, fornecedorId, produtoId);
    }

    private static class ProdutoFornecedorRowMapper implements RowMapper<ProdutoFornecedor> {
        @Override
        public ProdutoFornecedor mapRow(ResultSet rs, int rowNum) throws SQLException {
            ProdutoFornecedor pf = new ProdutoFornecedor();
            pf.setFkFornecedorId(rs.getInt("fk_fornecedor_id"));
            pf.setFkProdutoId(rs.getInt("fk_produto_id"));
            return pf;
        }
    }
}
