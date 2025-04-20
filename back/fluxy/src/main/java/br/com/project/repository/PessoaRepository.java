package br.com.project.repository;

import br.com.project.model.Person;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class PessoaRepository {

    private final JdbcTemplate jdbcTemplate;

    public PessoaRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void save(Person pessoa) {
        String sql = "INSERT INTO pessoa (id_pessoa, rua, numero, bairro, cidade, cep) VALUES (?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                pessoa.getIdPerson(),
                pessoa.getStreet(),
                pessoa.getNumber(),
                pessoa.getNeighborhood(),
                pessoa.getCity(),
                pessoa.getCep()
        );
    }

    public Optional<Person> findById(Integer id) {
        String sql = "SELECT * FROM pessoa WHERE id_pessoa = ?";
        List<Person> result = jdbcTemplate.query(sql, new PessoaRowMapper(), id);
        return result.stream().findFirst();
    }

    public List<Person> findAll() {
        String sql = "SELECT * FROM pessoa";
        return jdbcTemplate.query(sql, new PessoaRowMapper());
    }

    public void update(Person pessoa) {
        String sql = "UPDATE pessoa SET rua = ?, numero = ?, bairro = ?, cidade = ?, cep = ? WHERE id_pessoa = ?";
        jdbcTemplate.update(sql,
                pessoa.getStreet(),
                pessoa.getNumber(),
                pessoa.getNeighborhood(),
                pessoa.getCity(),
                pessoa.getCep(),
                pessoa.getIdPerson()
        );
    }

    public void deleteById(Integer id) {
        String sql = "DELETE FROM pessoa WHERE id_pessoa = ?";
        jdbcTemplate.update(sql, id);
    }

    private static class PessoaRowMapper implements RowMapper<Person> {
        @Override
        public Person mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Person(
                    rs.getInt("id_pessoa"),
                    rs.getString("rua"),
                    rs.getString("numero"),
                    rs.getString("bairro"),
                    rs.getString("cidade"),
                    rs.getString("cep")
            );
        }
    }
}
