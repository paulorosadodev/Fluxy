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

        List<Loja> resultado = jdbcTemplate.query(sql, new LojaRowMapper(), nome);

        return resultado.stream().findFirst();
    }

    public void salvar(Loja loja) {
        String sql = "INSERT INTO loja (nome, senha) VALUES ( ?, ?)";
        jdbcTemplate.update(sql, loja.getNome(), loja.getSenha());
    }

    private static class LojaRowMapper implements RowMapper<Loja> {
        @Override
        public Loja mapRow(ResultSet rs, int rowNum) throws SQLException {
            Loja loja = new Loja();
            loja.setNome(rs.getString("nome"));
            loja.setSenha(rs.getString("senha"));
            return loja;
        }
    }
}