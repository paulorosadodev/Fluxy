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

    public Integer findIdByPersonType(String identifier) {
        try {
            String sql;
            Object[] params;

            if (identifier.length() == 11) {
                sql =   "SELECT pe.id_pessoa" +
                        "FROM pessoa pe" +
                        "JOIN fisico pf ON pe.id_pessoa = pf.fk_cliente_id" +
                        "WHERE pf.cpf = ?";
                params = new Object[] { identifier };
            }
            else if (identifier.length() == 14) {
                sql =   "SELECT pe.id_pessoa" +
                        "FROM pessoa pe" +
                        "JOIN juridico pj ON pe.id_pessoa = pj.fk_cliente_id" +
                        "WHERE pj.cnpj = ?";
                params = new Object[] { identifier };
            }
            else {
                throw new IllegalArgumentException("Id inválido");
            }

            Integer id = jdbcTemplate.queryForObject(sql, params, Integer.class);
            return id != null ? id : -1;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}