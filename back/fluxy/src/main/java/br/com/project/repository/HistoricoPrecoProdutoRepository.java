package br.com.project.repository;

import br.com.project.model.HistoricoPrecoProduto;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class HistoricoPrecoProdutoRepository {

    private final JdbcTemplate jdbcTemplate;

    public HistoricoPrecoProdutoRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void save(HistoricoPrecoProduto historico) {
        String sql = "INSERT INTO historico_preco_produto (data, preco) VALUES (?, ?)";
        jdbcTemplate.update(sql,
                historico.getData(),
                historico.getPreco()
        );
    }

    public Optional<HistoricoPrecoProduto> findById(Integer id) {
        String sql = "SELECT * FROM historico_preco_produto WHERE id_historico_preco_produto = ?";
        List<HistoricoPrecoProduto> result = jdbcTemplate.query(sql, new HistoricoPrecoProdutoRowMapper(), id);
        return result.stream().findFirst();
    }

    public List<HistoricoPrecoProduto> findAll() {
        String sql = "SELECT * FROM historico_preco_produto";
        return jdbcTemplate.query(sql, new HistoricoPrecoProdutoRowMapper());
    }

    public void update(HistoricoPrecoProduto historico) {
        String sql = "UPDATE historico_preco_produto SET data = ?, preco = ? WHERE id_historico_preco_produto = ?";
        jdbcTemplate.update(sql,
                historico.getData(),
                historico.getPreco(),
                historico.getIdHistoricoPrecoProduto()
        );
    }

    public void deleteById(Integer id) {
        String sql = "DELETE FROM historico_preco_produto WHERE id_historico_preco_produto = ?";
        jdbcTemplate.update(sql, id);
    }

    private static class HistoricoPrecoProdutoRowMapper implements RowMapper<HistoricoPrecoProduto> {
        @Override
        public HistoricoPrecoProduto mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new HistoricoPrecoProduto(
                    rs.getInt("id_historico_preco_produto"),
                    rs.getDate("data"),
                    rs.getInt("preco")
            );
        }
    }
}
