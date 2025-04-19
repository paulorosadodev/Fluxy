package br.com.project.repository;

import br.com.project.model.ProdutoHistorico;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

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
                produtoHistorico.getFkHistoricoPrecoProdutoId()
        );
    }

    public List<ProdutoHistorico> findAll() {
        String sql = "SELECT * FROM produto_historico";
        return jdbcTemplate.query(sql, new ProdutoHistoricoRowMapper());
    }

    public void deleteByIds(Integer produtoId, Integer historicoId) {
        String sql = "DELETE FROM produto_historico WHERE fk_produto_id = ? AND fk_historicoprecoproduto_id = ?";
        jdbcTemplate.update(sql, produtoId, historicoId);
    }

    private static class ProdutoHistoricoRowMapper implements RowMapper<ProdutoHistorico> {
        @Override
        public ProdutoHistorico mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new ProdutoHistorico(
                    rs.getInt("fk_produto_id"),
                    rs.getInt("fk_historicoprecoproduto_id")
            );
        }
    }
}
