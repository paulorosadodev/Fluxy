package br.com.project.repository;

import br.com.project.dto.response.ClientCityCountDTO;
import br.com.project.dto.response.TopTierClientDTO;
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
            identifier = identifier.replaceAll("[^\\d]", "");
            String sql;
            Object[] params;

            if (identifier.length() == 11) {
                sql =   "SELECT c.id_cliente " +
                        "FROM cliente c " +
                        "JOIN pessoa pe ON pe.id_pessoa = c.id_cliente " +
                        "JOIN fisico pf ON pf.fk_cliente_id = pe.id_pessoa " +
                        "WHERE pf.cpf = ?";
                params = new Object[] { identifier };
            }
            else if (identifier.length() == 14) {
                sql =   "SELECT c.id_cliente " +
                        "FROM cliente c " +
                        "JOIN pessoa pe ON pe.id_pessoa = c.id_cliente " +
                        "JOIN juridico pj ON pj.fk_cliente_id = pe.id_pessoa " +
                        "WHERE pj.cnpj = ?";
                params = new Object[]{ identifier };
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

    public int getTotalClientsCount() {
        try {
            String sql = "SELECT COUNT(*) FROM cliente";
            return jdbcTemplate.queryForObject(sql, Integer.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<ClientCityCountDTO> getTotalClientByCity() {
        try {
            String sql = "SELECT pe.cidade, COUNT(*) AS total " +
                         "FROM pessoa pe " +
                         "JOIN fluxy_db.cliente c on pe.id_pessoa = c.id_cliente " +
                         "GROUP BY pe.cidade";
            return jdbcTemplate.query(sql, (rs, rowNum) -> new ClientCityCountDTO(
                    rs.getString("cidade"),
                    rs.getInt("total")
            ));
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public List<TopTierClientDTO> getTopTierClientByPurchases() {
        try {
            String sql = "SELECT " +
                    "    COALESCE(pf.nome, pj.razao_social) AS nome_cliente, " +
                    "    COUNT(co.numero) AS total_compras " +
                    "FROM cliente c " +
                    "         LEFT JOIN fisico pf ON c.id_cliente = pf.fk_cliente_id " +
                    "         LEFT JOIN juridico pj ON c.id_cliente = pj.fk_cliente_id " +
                    "         JOIN compra co ON c.id_cliente = co.fk_cliente_id " +
                    "GROUP BY nome_cliente " +
                    "ORDER BY total_compras DESC";
            return jdbcTemplate.query(sql, (rs, rowNum) -> new TopTierClientDTO(
                    rs.getString("nome_cliente"),
                    rs.getInt("totalPurchases")
            ));
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public int getTotalPhysicalClientsCount() {
        try {
            String sql = "SELECT COUNT(*) FROM cliente " +
                         "JOIN pessoa pe ON pe.id_pessoa = cliente.id_cliente " +
                         "JOIN fisico pf ON pf.fk_cliente_id = pe.id_pessoa";
            return jdbcTemplate.queryForObject(sql, Integer.class);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public int getTotalJuridicalClientsCount() {
        try {
            String sql = "SELECT COUNT(*) FROM cliente " +
                         "JOIN pessoa pe ON pe.id_pessoa = cliente.id_cliente " +
                         "JOIN juridico pj ON pj.fk_cliente_id = pe.id_pessoa";
            return jdbcTemplate.queryForObject(sql, Integer.class);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public List<TopTierClientDTO> getMostActiveClients() {
        try {
            String sql = "SELECT pf.nome, COUNT(*) AS total " +
                    "FROM fisico pf " +
                    "JOIN cliente c ON pf.fk_cliente_id = c.id_cliente " +
                    "GROUP BY pf.nome " +
                    "ORDER BY TOTAL DESC " +
                    "LIMIT 5";
            return jdbcTemplate.query(sql, (rs, rowNum) -> new TopTierClientDTO(
                    rs.getString("name"),
                    rs.getInt("totalPurchases")
            ));
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

}