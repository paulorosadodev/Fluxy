package br.com.project.repository;

import br.com.project.model.Subgerente;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class SubgerenteRepository {

    private final JdbcTemplate jdbcTemplate;

    public SubgerenteRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void save(Subgerente subgerente) {
        String sql = "INSERT INTO subgerente (fk_funcionario_id_funcionario, turno_atuacao) VALUES (?, ?)";
        jdbcTemplate.update(sql,
                subgerente.getFkFuncionarioIdFuncionario(),
                subgerente.getTurnoAtuacao()
        );
    }

    public Optional<Subgerente> findById(Integer id) {
        String sql = "SELECT * FROM subgerente WHERE fk_funcionario_id_funcionario = ?";
        List<Subgerente> result = jdbcTemplate.query(sql, new SubgerenteRowMapper(), id);
        return result.stream().findFirst();
    }

    public List<Subgerente> findAll() {
        String sql = "SELECT * FROM subgerente";
        return jdbcTemplate.query(sql, new SubgerenteRowMapper());
    }

    public void update(Subgerente subgerente) {
        String sql = "UPDATE subgerente SET turno_atuacao = ? WHERE fk_funcionario_id_funcionario = ?";
        jdbcTemplate.update(sql,
                subgerente.getTurnoAtuacao(),
                subgerente.getFkFuncionarioIdFuncionario()
        );
    }

    public void deleteById(Integer id) {
        String sql = "DELETE FROM subgerente WHERE fk_funcionario_id_funcionario = ?";
        jdbcTemplate.update(sql, id);
    }

    private static class SubgerenteRowMapper implements RowMapper<Subgerente> {
        @Override
        public Subgerente mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Subgerente(
                    rs.getInt("fk_funcionario_id_funcionario"),
                    rs.getString("turno_atuacao")
            );
        }
    }
}
