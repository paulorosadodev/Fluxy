package br.com.project.repository;

import br.com.project.model.Employer;
import br.com.project.model.Person;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.List;
import java.util.Optional;

@Repository
public class EmployerRepository {

    private final JdbcTemplate jdbcTemplate;

    public EmployerRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Integer save(Employer employer) {
        try {
            String sql = "INSERT INTO funcionario (id_funcionario, matricula, nome, cpf, salario, setor, turno, funcao, id_supervisor) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            KeyHolder keyHolder = new GeneratedKeyHolder();

            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setInt(1, employer.getIdPerson());
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
                    ps.setNull(9, Types.INTEGER);
                }
                return ps;
            }, keyHolder);

            return keyHolder.getKey() != null ? keyHolder.getKey().intValue() : null;

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public Optional<Employer> findById(Integer id) {
        try {
            String sql = """
            SELECT 
                f.id_funcionario,
                f.matricula,
                f.nome,
                f.cpf,
                f.salario,
                f.setor,
                f.turno,
                f.funcao,
                f.id_supervisor,
                p.rua,
                p.numero,
                p.bairro,
                p.cidade,
                p.cep
            FROM funcionario f
            JOIN pessoa p ON f.id_funcionario = p.id_pessoa
            WHERE f.id_funcionario = ?
            """;

            List<Employer> result = jdbcTemplate.query(sql, new FuncionarioRowMapper(), id);
            return result.stream().findFirst();

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public List<Employer> findAll() {
        try {
            String sql = """
            SELECT 
                f.id_funcionario,
                f.matricula,
                f.nome,
                f.cpf,
                f.salario,
                f.setor,
                f.turno,
                f.funcao,
                f.id_supervisor,
                p.rua,
                p.numero,
                p.bairro,
                p.cidade,
                p.cep
            FROM funcionario f
            JOIN pessoa p ON f.id_funcionario = p.id_pessoa
            """;

            return jdbcTemplate.query(sql, new FuncionarioRowMapper());

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public void update(Employer employer) {
        try {
            String updatePersonSql = "UPDATE pessoa SET rua = ?, numero = ?, bairro = ?, cidade = ?, cep = ? WHERE id_pessoa = ?";
            jdbcTemplate.update(updatePersonSql,
                    employer.getPerson().getStreet(),
                    employer.getPerson().getNumber(),
                    employer.getPerson().getNeighborhood(),
                    employer.getPerson().getCity(),
                    employer.getPerson().getCep(),
                    employer.getIdPerson()
            );

            String updateFuncionarioSql = "UPDATE funcionario SET matricula = ?, nome = ?, cpf = ?, salario = ?, setor = ?, turno = ?, funcao = ?, id_supervisor = ? WHERE id_funcionario = ?";
            jdbcTemplate.update(updateFuncionarioSql,
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

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public void deleteById(Integer id) {
        try {
            String sql = "DELETE FROM funcionario WHERE id_funcionario = ?";
            jdbcTemplate.update(sql, id);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private class FuncionarioRowMapper implements RowMapper<Employer> {
        @Override
        public Employer mapRow(ResultSet rs, int rowNum) throws SQLException {
            Employer e = new Employer();
            e.setIdEmployee(rs.getInt("id_funcionario"));
            e.setIdPerson(rs.getInt("id_funcionario"));
            e.setEmployeeNumber(rs.getString("matricula"));
            e.setName(rs.getString("nome"));
            e.setCpf(rs.getString("cpf"));
            e.setSalary(rs.getDouble("salario"));
            e.setSectorOfActivity(rs.getString("setor"));
            e.setWorkShift(rs.getString("turno"));
            e.setRole(rs.getString("funcao"));
            e.setIdSupervisor(rs.getObject("id_supervisor") != null ? rs.getInt("id_supervisor") : null);

            Person p = new Person();
            p.setStreet(rs.getString("rua"));
            p.setNumber(rs.getString("numero"));
            p.setNeighborhood(rs.getString("bairro"));
            p.setCity(rs.getString("cidade"));
            p.setCep(rs.getString("cep"));
            e.setPerson(p);

            e.setPhones(findPhonesByIdPerson(e.getIdPerson()));

            return e;
        }
    }

    private List<String> findPhonesByIdPerson(Integer idPerson) {
        try {
            String sql = "SELECT numero FROM telefone WHERE id_telefone = ?";
            return jdbcTemplate.query(sql, (rs, rn) -> rs.getString("numero"), idPerson);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public Optional<Integer> findEmployeeIdByMatricula(String matricula) {
        try {
            String sql = "SELECT id_funcionario FROM funcionario WHERE matricula = ?";
            Integer id = jdbcTemplate.queryForObject(sql, Integer.class, matricula);
            return Optional.ofNullable(id);
        } catch (Exception e) {
            return Optional.empty() ;
        }
    }

    public boolean existsByCpf(String cpf) {
        try {
            String sql = "SELECT COUNT(*) FROM funcionario WHERE cpf = ?";
            Integer count = jdbcTemplate.queryForObject(sql, Integer.class, cpf);
            return count != null && count > 0;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
