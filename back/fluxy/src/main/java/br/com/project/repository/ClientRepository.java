package br.com.project.repository;

import br.com.project.model.Client;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class ClientRepository {

    private final JdbcTemplate jdbcTemplate;

    public ClientRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void save(Client client) {
        String sql = "INSERT INTO cliente (cliente.id) VALUES (?)";
        jdbcTemplate.update(sql, client.getIdPerson());
    }

    public Optional<Client> findById(Integer id) {
        String sql = "SELECT * FROM cliente WHERE cliente.id = ?";
        List<Client> result = jdbcTemplate.query(sql, new ClienteRowMapper(), id);
        return result.stream().findFirst();
    }

    public List<Client> findAll() {
        String sql = "SELECT * FROM cliente";
        return jdbcTemplate.query(sql, new ClienteRowMapper());
    }

    public void update(Client client) {
        String sql = "UPDATE cliente SET cliente.id = ? WHERE cliente.id = ?";
        jdbcTemplate.update(sql, client.getIdPerson(), client.getIdClient());
    }

    public void deleteById(Integer id) {
        String sql = "DELETE FROM cliente WHERE cliente.id = ?";
        jdbcTemplate.update(sql, id);
    }

    private static class ClienteRowMapper implements RowMapper<Client> {
        @Override
        public Client mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Client(
                    rs.getInt("id_cliente"),
                    rs.getInt("id_cliente")
            );
        }
    }
}
