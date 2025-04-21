package br.com.project.repository;

import br.com.project.model.Person;
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
public class PersonRepository {

    private final JdbcTemplate jdbcTemplate;

    public PersonRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Novo método correto
    public Integer saveAndReturnId(Person person) {
        String sql = "INSERT INTO pessoa (rua, numero, bairro, cidade, cep) VALUES (?, ?, ?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, person.getStreet());
            ps.setString(2, person.getNumber());
            ps.setString(3, person.getNeighborhood());
            ps.setString(4, person.getCity());
            ps.setString(5, person.getCep());
            return ps;
        }, keyHolder);

        return keyHolder.getKey().intValue(); // Retorna o ID gerado
    }

    public Optional<Person> findById(Integer id) {
        String sql = "SELECT * FROM pessoa WHERE id_pessoa = ?";
        List<Person> result = jdbcTemplate.query(sql, new PersonRowMapper(), id);
        return result.stream().findFirst();
    }

    public List<Person> findAll() {
        String sql = "SELECT * FROM pessoa";
        return jdbcTemplate.query(sql, new PersonRowMapper());
    }

    public void update(Person person) {
        String sql = "UPDATE pessoa SET rua = ?, numero = ?, bairro = ?, cidade = ?, cep = ? WHERE id_pessoa = ?";
        jdbcTemplate.update(sql,
                person.getStreet(),
                person.getNumber(),
                person.getNeighborhood(),
                person.getCity(),
                person.getCep(),
                person.getIdPerson()
        );
    }

    public void deleteById(Integer id) {
        String sql = "DELETE FROM pessoa WHERE id_pessoa = ?";
        jdbcTemplate.update(sql, id);
    }

    private static class PersonRowMapper implements RowMapper<Person> {
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
