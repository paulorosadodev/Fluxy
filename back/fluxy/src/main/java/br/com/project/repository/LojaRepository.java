package br.com.project.repository;

import br.com.project.model.Loja;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class LojaRepository {

    private final JdbcTemplate jdbcTemplate;

    public LojaRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Optional<Loja> encontrarPorNome(String nome) {
        String sql = "SELECT * FROM loja WHERE nome = ?";

        List<Loja> resultado = jdbcTemplate.query(sql, new Object[]{nome}, new LojaRowMapper());

        if (resultado.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(resultado.get(0));
    }

    public void salvar(Loja loja) {
        String sql = "INSERT INTO loja (id, nome, senha) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, loja.getId(), loja.getUsername(), loja.getSenha());
    }

    private static class LojaRowMapper implements RowMapper<Loja> {
        @Override
        public Loja mapRow(ResultSet rs, int rowNum) throws SQLException {
            Loja loja = new Loja();
            loja.setId(rs.getInt("id"));
            loja.setUsername(rs.getString("nome"));
            loja.setSenha(rs.getString("senha"));
            return loja;
        }
    }
}