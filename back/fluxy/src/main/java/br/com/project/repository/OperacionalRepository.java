package br.com.project.repository;

import br.com.project.model.Operacional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class OperacionalRepository {

    private final JdbcTemplate jdbcTemplate;

    public OperacionalRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void save(Operacional operacional) {
        String sql = "INSERT INTO operacional (fk_funcionario_id_funcionario, funcao) VALUES (?, ?)";
        jdbcTemplate.update(sql,
                operacional.getFkFuncionarioIdFuncionario(),
                operacional.getFuncao()
        );
    }

    public Optional<Operacional> findById(Integer id) {
        String sql = "SELECT * FROM operacional WHERE fk_funcionario_id_funcionario = ?";
        List<Operacional> result = jdbcTemplate.query(sql, new OperacionalRowMapper(), id);
        return result.stream().findFirst();
    }

    public List<Operacional> findAll() {
        String sql = "SELECT * FROM operacional";
        return jdbcTemplate.query(sql, new OperacionalRowMapper());
    }

    public void update(Operacional operacional) {
        String sql = "UPDATE operacional SET funcao = ? WHERE fk_funcionario_id_funcionario = ?";
        jdbcTemplate.update(sql,
                operacional.getFuncao(),
                operacional.getFkFuncionarioIdFuncionario()
        );
    }

    public void deleteById(Integer id) {
        String sql = "DELETE FROM operacional WHERE fk_funcionario_id_funcionario = ?";
        jdbcTemplate.update(sql, id);
    }

    private static class OperacionalRowMapper implements RowMapper<Operacional> {
        @Override
        public Operacional mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Operacional(
                    rs.getInt("fk_funcionario_id_funcionario"),
                    rs.getString("funcao")
            );
        }
    }
}
