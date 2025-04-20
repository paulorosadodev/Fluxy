package br.com.project.repository;

import br.com.project.model.Employer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class FuncionarioRepository {

    private final JdbcTemplate jdbcTemplate;

    public FuncionarioRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void save(Employer funcionario) {
        String sql = "INSERT INTO funcionario (id_funcionario, matricula, nome, cpf, salario, setor, turno, funcao, id_supervisor) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                funcionario.getIdPessoa(), // O id_funcionario é o id_pessoa
                funcionario.getMatricula(),
                funcionario.getNome(),
                funcionario.getCpf(),
                funcionario.getSalario(),
                funcionario.getSetor(),
                funcionario.getTurno(),
                funcionario.getFuncao(),
                funcionario.getIdSupervisor()
        );
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

    public void update(Employer funcionario) {
        String sql = "UPDATE funcionario SET matricula = ?, nome = ?, cpf = ?, salario = ?, setor = ?, turno = ?, funcao = ?, id_supervisor = ? WHERE id_funcionario = ?";
        jdbcTemplate.update(sql,
                funcionario.getMatricula(),
                funcionario.getNome(),
                funcionario.getCpf(),
                funcionario.getSalario(),
                funcionario.getSetor(),
                funcionario.getTurno(),
                funcionario.getFuncao(),
                funcionario.getIdSupervisor(),
                funcionario.getIdFuncionario()
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
                    rs.getInt("id_funcionario"), // id_pessoa é o mesmo id_funcionario
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
