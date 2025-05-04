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

    // Método para salvar e retornar o ID gerado
    public Integer saveAndReturnId(Person person) {
        try {
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

        } catch (Exception e) {
            throw new RuntimeException("Erro ao salvar pessoa no banco de dados: " + e.getMessage());
        }
    }

    public Optional<Person> findById(Integer id) {
        try {
            String sql = "SELECT * FROM pessoa WHERE id_pessoa = ?";
            List<Person> result = jdbcTemplate.query(sql, new PersonRowMapper(), id);
            return result.stream().findFirst();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar pessoa: " + e.getMessage());
        }
    }

    public List<Person> findAll() {
        try {
            String sql = "SELECT * FROM pessoa";
            return jdbcTemplate.query(sql, new PersonRowMapper());
        } catch (Exception e) {
            throw new RuntimeException("Erro ao listar pessoas: " + e.getMessage());
        }
    }

    public void update(Person person) {
        try {
            String sql = "UPDATE pessoa SET rua = ?, numero = ?, bairro = ?, cidade = ?, cep = ? WHERE id_pessoa = ?";
            jdbcTemplate.update(sql,
                    person.getStreet(),
                    person.getNumber(),
                    person.getNeighborhood(),
                    person.getCity(),
                    person.getCep(),
                    person.getIdPerson()
            );
        } catch (Exception e) {
            throw new RuntimeException("Erro ao atualizar pessoa: " + e.getMessage());
        }
    }

    public void deleteById(Integer id) {
        try {
            String sql = "DELETE FROM pessoa WHERE id_pessoa = ?";
            jdbcTemplate.update(sql, id);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao deletar pessoa: " + e.getMessage());
        }
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
