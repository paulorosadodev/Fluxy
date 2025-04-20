package br.com.project.repository;

import br.com.project.model.ProdutoHistorico;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class ProdutoHistoricoRepository {

    private final JdbcTemplate jdbcTemplate;

    public ProdutoHistoricoRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void save(ProdutoHistorico produtoHistorico) {
        String sql = "INSERT INTO produto_historico (fk_produto_id, fk_historicoprecoproduto_id) VALUES (?, ?)";
        jdbcTemplate.update(sql,
                produtoHistorico.getFkProdutoId(),
                produtoHistorico.getFkHistoricoPrecoProdutoId());
    }

    public Optional<ProdutoHistorico> findByIds(Integer produtoId, Integer historicoPrecoProdutoId) {
        String sql = "SELECT * FROM produto_historico WHERE fk_produto_id = ? AND fk_historicoprecoproduto_id = ?";
        List<ProdutoHistorico> result = jdbcTemplate.query(sql, new ProdutoHistoricoRowMapper(), produtoId, historicoPrecoProdutoId);
        return result.stream().findFirst();
    }

    public List<ProdutoHistorico> findAll() {
        String sql = "SELECT * FROM produto_historico";
        return jdbcTemplate.query(sql, new ProdutoHistoricoRowMapper());
    }

    public void delete(Integer produtoId, Integer historicoPrecoProdutoId) {
        String sql = "DELETE FROM produto_historico WHERE fk_produto_id = ? AND fk_historicoprecoproduto_id = ?";
        jdbcTemplate.update(sql, produtoId, historicoPrecoProdutoId);
    }

    private static class ProdutoHistoricoRowMapper implements RowMapper<ProdutoHistorico> {
        @Override
        public ProdutoHistorico mapRow(ResultSet rs, int rowNum) throws SQLException {
            ProdutoHistorico ph = new ProdutoHistorico();
            ph.setFkProdutoId(rs.getInt("fk_produto_id"));
            ph.setFkHistoricoPrecoProdutoId(rs.getInt("fk_historicoprecoproduto_id"));
            return ph;
        }
    }
}
