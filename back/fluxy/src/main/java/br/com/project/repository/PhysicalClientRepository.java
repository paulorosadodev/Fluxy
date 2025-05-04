package br.com.project.repository;

import br.com.project.model.PhysicalClient;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class PhysicalClientRepository {

    private final JdbcTemplate jdbcTemplate;

    public PhysicalClientRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Integer save(PhysicalClient client) {
        try {
            String sqlPessoa = "INSERT INTO pessoa (rua, numero, bairro, cidade, cep) VALUES (?, ?, ?, ?, ?)";
            KeyHolder keyHolder = new GeneratedKeyHolder();

            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(sqlPessoa, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, client.getStreet());
                ps.setString(2, client.getNumber());
                ps.setString(3, client.getNeighborhood());
                ps.setString(4, client.getCity());
                ps.setString(5, client.getCep());
                return ps;
            }, keyHolder);

            Integer idPessoa = keyHolder.getKey().intValue();

            String sqlCliente = "INSERT INTO cliente (id_cliente) VALUES (?)";
            jdbcTemplate.update(sqlCliente, idPessoa);

            String sqlFisico = "INSERT INTO fisico (fk_cliente_id, nome, cpf) VALUES (?, ?, ?)";
            jdbcTemplate.update(sqlFisico, idPessoa, client.getName(), client.getCpf());

            insertPhone(idPessoa, client.getPhone());

            return idPessoa;

        } catch (Exception e) {
            throw new RuntimeException("Erro ao salvar cliente físico: " + e.getMessage(), e);
        }
    }

    public boolean existsByCpf(String cpf) {
        try {
            String sql = "SELECT COUNT(*) FROM fisico WHERE cpf = ?";
            Integer count = jdbcTemplate.queryForObject(sql, Integer.class, cpf);
            return count != null && count > 0;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao verificar existência de CPF: " + e.getMessage(), e);
        }
    }

    public Optional<PhysicalClient> findById(Integer id) {
        try {
            String sql = """
            SELECT 
                c.id_cliente,
                f.nome,
                f.cpf,
                p.rua,
                p.numero,
                p.bairro,
                p.cidade,
                p.cep,
                t.numero AS telefone
            FROM fisico f
            INNER JOIN cliente c ON f.fk_cliente_id = c.id_cliente
            INNER JOIN pessoa p ON c.id_cliente = p.id_pessoa
            LEFT JOIN telefone t ON p.id_pessoa = t.id_telefone
            WHERE c.id_cliente = ?
        """;

            List<PhysicalClient> result = jdbcTemplate.query(sql, new PhysicalClientRowMapper(), id);
            PhysicalClient client = result.stream().findFirst().orElse(null);

            if (client != null) {
                client.setPhone(findPhoneByIdPerson(id));
            }

            return Optional.ofNullable(client);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar cliente físico por ID: " + e.getMessage(), e);
        }
    }

    public List<PhysicalClient> findAll() {
        try {
            String sql = """
            SELECT 
                c.id_cliente,
                f.nome,
                f.cpf,
                p.rua,
                p.numero,
                p.bairro,
                p.cidade,
                p.cep,
                t.numero AS telefone
            FROM fisico f
            INNER JOIN cliente c ON f.fk_cliente_id = c.id_cliente
            INNER JOIN pessoa p ON c.id_cliente = p.id_pessoa
            LEFT JOIN telefone t ON p.id_pessoa = t.id_telefone
        """;

            List<PhysicalClient> clients = jdbcTemplate.query(sql, new PhysicalClientRowMapper());

            clients.forEach(client -> client.setPhone(findPhoneByIdPerson(client.getId())));

            return clients;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao listar clientes físicos: " + e.getMessage(), e);
        }
    }

    public void update(Integer id, PhysicalClient client) {
        try {
            String sqlPessoa = "UPDATE pessoa SET rua = ?, numero = ?, bairro = ?, cidade = ?, cep = ? WHERE id_pessoa = ?";
            jdbcTemplate.update(sqlPessoa,
                    client.getStreet(), client.getNumber(), client.getNeighborhood(), client.getCity(),
                    client.getCep(), id);

            String sqlFisico = "UPDATE fisico SET nome = ?, cpf = ? WHERE fk_cliente_id = ?";
            jdbcTemplate.update(sqlFisico,
                    client.getName(), client.getCpf(), id);

            deletePhoneByIdPerson(id);
            insertPhone(id, client.getPhone());

        } catch (Exception e) {
            throw new RuntimeException("Erro ao atualizar cliente físico: " + e.getMessage(), e);
        }
    }

    public void deleteById(Integer id) {
        try {
            String sqlTelefone = "DELETE FROM telefone WHERE id_telefone = ?";
            String sqlFisico = "DELETE FROM fisico WHERE fk_cliente_id = ?";
            String sqlCliente = "DELETE FROM cliente WHERE id_cliente = ?";
            String sqlPessoa = "DELETE FROM pessoa WHERE id_pessoa = ?";

            jdbcTemplate.update(sqlTelefone, id);
            jdbcTemplate.update(sqlFisico, id);
            jdbcTemplate.update(sqlCliente, id);
            jdbcTemplate.update(sqlPessoa, id);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao deletar cliente físico: " + e.getMessage(), e);
        }
    }

    private List<String> findPhoneByIdPerson(Integer idPerson) {
        try {
            String sql = "SELECT numero FROM telefone WHERE id_telefone = ?";
            return jdbcTemplate.query(
                    sql,
                    (rs, rn) -> rs.getString("numero"),
                    idPerson
            );
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar telefones do cliente físico: " + e.getMessage(), e);
        }
    }

    private void deletePhoneByIdPerson(Integer idPerson) {
        try {
            String sql = "DELETE FROM telefone WHERE id_telefone = ?";
            jdbcTemplate.update(sql, idPerson);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao deletar telefones do cliente físico: " + e.getMessage(), e);
        }
    }

    private void insertPhone(Integer idPerson, List<String> phones) {
        try {
            if (phones != null && !phones.isEmpty()) {
                for (String numero : phones) {
                    if (numero != null && !numero.isBlank()) {
                        String sqlTelefone = "INSERT INTO telefone (numero, id_telefone) VALUES (?, ?)";
                        jdbcTemplate.update(sqlTelefone, numero.trim(), idPerson);
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao inserir telefones do cliente físico: " + e.getMessage(), e);
        }
    }

    private class PhysicalClientRowMapper implements RowMapper<PhysicalClient> {
        @Override
        public PhysicalClient mapRow(ResultSet rs, int rowNum) throws SQLException {
            PhysicalClient client = new PhysicalClient(
                    rs.getInt("id_cliente"),
                    rs.getString("nome"),
                    rs.getString("cpf"),
                    rs.getString("rua"),
                    rs.getString("numero"),
                    rs.getString("bairro"),
                    rs.getString("cidade"),
                    rs.getString("cep"),
                    new ArrayList<>()
            );

            // Verificando se o número de telefone existe e atribuindo
            String phone = rs.getString("telefone");
            if (phone != null) {
                client.getPhone().add(phone);  // Adicionando o telefone retornado
            }

            return client;
        }
    }

}
