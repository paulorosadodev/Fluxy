package br.com.project.repository;

import br.com.project.model.Employer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

@Repository
public class EmployerRepository {

    private final JdbcTemplate jdbcTemplate;

    public EmployerRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Integer save(Employer employer) {
        String sql = "INSERT INTO funcionario (id_funcionario, matricula, nome, cpf, salario, setor, turno, funcao, id_supervisor) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, employer.getIdPerson()); // id_funcionario é o id_pessoa (não gerado aqui, já vem)
            ps.setString(2, employer.getEmployeeNumber());
            ps.setString(3, employer.getName());
            ps.setString(4, employer.getCpf());
            ps.setDouble(5, employer.getSalary());
            ps.setString(6, employer.getSectorOfActivity());
            ps.setString(7, employer.getWorkShift());
            ps.setString(8, employer.getRole());
            if (employer.getIdSupervisor() != null) {
                ps.setInt(9, employer.getIdSupervisor());
            } else {
                ps.setNull(9, java.sql.Types.INTEGER);
            }
            return ps;
        }, keyHolder);

        // Retorna o id_funcionario se quiser usar depois
        return keyHolder.getKey() != null ? keyHolder.getKey().intValue() : null;
    }


    public Optional<Employer> findById(Integer id) {
        String sql = "SELECT * FROM funcionario WHERE id_funcionario = ?";
        List<Employer> result = jdbcTemplate.query(sql, new FuncionarioRowMapper(), id);
        return result.stream().findFirst();
    }

    public List<Employer> findAll() {
        String sql = "SELECT * FROM funcionario";
        return jdbcTemplate.query(sql, new FuncionarioRowMapper());
    }

    public void update(Employer employer) {
        String sql = "UPDATE funcionario SET matricula = ?, nome = ?, cpf = ?, salario = ?, setor = ?, turno = ?, funcao = ?, id_supervisor = ? WHERE id_funcionario = ?";
        jdbcTemplate.update(sql,
                employer.getEmployeeNumber(),
                employer.getName(),
                employer.getCpf(),
                employer.getSalary(),
                employer.getSectorOfActivity(),
                employer.getWorkShift(),
                employer.getRole(),
                employer.getIdSupervisor(),
                employer.getIdEmployee()
        );
    }

    public void deleteById(Integer id) {
        String sql = "DELETE FROM funcionario WHERE id_funcionario = ?";
        jdbcTemplate.update(sql, id);
    }

    private static class FuncionarioRowMapper implements RowMapper<Employer> {
        @Override
        public Employer mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Employer(
                    rs.getInt("id_funcionario"),
                    rs.getInt("id_funcionario"), // id_pessoa = id_funcionario
                    rs.getString("matricula"),
                    rs.getString("nome"),
                    rs.getString("cpf"),
                    rs.getDouble("salario"),
                    rs.getString("setor"),
                    rs.getString("turno"),
                    rs.getString("funcao"),
                    rs.getObject("id_supervisor") != null ? rs.getInt("id_supervisor") : null
            );
        }
    }
}
