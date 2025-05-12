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
        try {
            String sql = "INSERT INTO cliente (id_cliente) VALUES (?)";
            jdbcTemplate.update(sql, client.getIdPerson());
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public Optional<Client> findById(Integer id) {
        try {
            String sql = "SELECT * FROM cliente WHERE id_cliente = ?";
            List<Client> result = jdbcTemplate.query(sql, new ClienteRowMapper(), id);
            return result.stream().findFirst();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public List<Client> findAll() {
        try {
            String sql = "SELECT * FROM cliente";
            return jdbcTemplate.query(sql, new ClienteRowMapper());
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public void update(Client client) {
        try {
            String sql = "UPDATE cliente SET id_cliente = ? WHERE id_cliente = ?";
            jdbcTemplate.update(sql, client.getIdPerson(), client.getIdClient());
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public void deleteById(Integer id) {
        try {
            String sql = "DELETE FROM cliente WHERE id_cliente = ?";
            jdbcTemplate.update(sql, id);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
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