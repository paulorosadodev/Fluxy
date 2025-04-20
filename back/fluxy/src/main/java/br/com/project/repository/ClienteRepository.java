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
public class ClienteRepository {

    private final JdbcTemplate jdbcTemplate;

    public ClienteRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void save(Client cliente) {
        String sql = "INSERT INTO cliente (id_cliente) VALUES (?)";
        jdbcTemplate.update(sql, cliente.getIdPessoa());
    }

    public Optional<Client> findById(Integer id) {
        String sql = "SELECT * FROM cliente WHERE id_cliente = ?";
        List<Client> result = jdbcTemplate.query(sql, new ClienteRowMapper(), id);
        return result.stream().findFirst();
    }

    public List<Client> findAll() {
        String sql = "SELECT * FROM cliente";
        return jdbcTemplate.query(sql, new ClienteRowMapper());
    }

    public void update(Client cliente) {
        String sql = "UPDATE cliente SET id_cliente = ? WHERE id_cliente = ?";
        jdbcTemplate.update(sql, cliente.getIdPessoa(), cliente.getIdCliente());
    }

    public void deleteById(Integer id) {
        String sql = "DELETE FROM cliente WHERE id_cliente = ?";
        jdbcTemplate.update(sql, id);
    }

    private static class ClienteRowMapper implements RowMapper<Client> {
        @Override
        public Client mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Client(
                    rs.getInt("id_cliente"),
                    rs.getInt("id_cliente") // O id_pessoa é o mesmo id_cliente (relacionado)
            );
        }
    }
}
