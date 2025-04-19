package br.com.project.repository;

import br.com.project.model.Gerente;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class GerenteRepository {

    private final JdbcTemplate jdbcTemplate;

    public GerenteRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void save(Gerente gerente) {
        String sql = "INSERT INTO gerente (fk_funcionario_matricula, setor_atuacao) VALUES (?, ?)";
        jdbcTemplate.update(sql,
                gerente.getFkFuncionarioMatricula(),
                gerente.getSetorAtuacao()
        );
    }

    public Optional<Gerente> findById(Integer id) {
        String sql = "SELECT * FROM gerente WHERE fk_funcionario_matricula = ?";
        List<Gerente> result = jdbcTemplate.query(sql, new GerenteRowMapper(), id);
        return result.stream().findFirst();
    }

    public List<Gerente> findAll() {
        String sql = "SELECT * FROM gerente";
        return jdbcTemplate.query(sql, new GerenteRowMapper());
    }

    public void update(Gerente gerente) {
        String sql = "UPDATE gerente SET setor_atuacao = ? WHERE fk_funcionario_matricula = ?";
        jdbcTemplate.update(sql,
                gerente.getSetorAtuacao(),
                gerente.getFkFuncionarioMatricula()
        );
    }

    public void deleteById(Integer id) {
        String sql = "DELETE FROM gerente WHERE fk_funcionario_matricula = ?";
        jdbcTemplate.update(sql, id);
    }

    private static class GerenteRowMapper implements RowMapper<Gerente> {
        @Override
        public Gerente mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Gerente(
                    rs.getInt("fk_funcionario_matricula"),
                    rs.getString("setor_atuacao")
            );
        }
    }
}
