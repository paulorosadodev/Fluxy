package br.com.project.repository;

import br.com.project.model.LegalClient;
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
public class LegalClientRepository {

    private final JdbcTemplate jdbcTemplate;

    public LegalClientRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Integer save(LegalClient client) {
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

            String sqlJuridico = "INSERT INTO juridico (fk_cliente_id, inscr_estadual, cnpj, razao_social) VALUES (?, ?, ?, ?)";
            jdbcTemplate.update(sqlJuridico, idPessoa, client.getStateRegistration(), client.getCnpj(),
                    client.getLegalName());

            // Inserir telefones se existirem
            if (client.getPhone() != null && !client.getPhone().isEmpty()) {
                for (String numero : client.getPhone()) {
                    if (numero != null && !numero.isBlank()) {
                        String sqlTelefone = "INSERT INTO telefone (numero, id_telefone) VALUES (?, ?)";
                        jdbcTemplate.update(sqlTelefone, numero, idPessoa);
                    }
                }
            }
            return idPessoa;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao salvar cliente jurídico: " + e.getMessage());
        }
    }

    public Optional<LegalClient> findById(Integer id) {
        try {
            String sql = """
                SELECT 
                    c.id_cliente,
                    j.razao_social,
                    j.cnpj,
                    j.inscr_estadual,
                    p.rua,
                    p.numero,
                    p.bairro,
                    p.cidade,
                    p.cep
                FROM juridico j
                INNER JOIN cliente c ON j.fk_cliente_id = c.id_cliente
                INNER JOIN pessoa p ON c.id_cliente = p.id_pessoa
                WHERE c.id_cliente = ?
            """;

            List<LegalClient> result = jdbcTemplate.query(sql, new LegalClientRowMapper(), id);
            return result.stream().findFirst();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar cliente jurídico: " + e.getMessage());
        }
    }

    public List<LegalClient> findAll() {
        try {
            String sql = """
                SELECT 
                    c.id_cliente,
                    j.razao_social,
                    j.cnpj,
                    j.inscr_estadual,
                    p.rua,
                    p.numero,
                    p.bairro,
                    p.cidade,
                    p.cep
                FROM juridico j
                INNER JOIN cliente c ON j.fk_cliente_id = c.id_cliente
                INNER JOIN pessoa p ON c.id_cliente = p.id_pessoa
            """;

            return jdbcTemplate.query(sql, new LegalClientRowMapper());
        } catch (Exception e) {
            throw new RuntimeException("Erro ao listar clientes jurídicos: " + e.getMessage());
        }
    }

    public void update(Integer id, LegalClient client) {
        try {
            String sqlPessoa = "UPDATE pessoa SET rua = ?, numero = ?, bairro = ?, cidade = ?, cep = ? WHERE id_pessoa = ?";
            jdbcTemplate.update(sqlPessoa,
                    client.getStreet(), client.getNumber(), client.getNeighborhood(), client.getCity(),
                    client.getCep(), id);

            String sqlJuridico = "UPDATE juridico SET razao_social = ?, cnpj = ?, inscr_estadual = ? WHERE fk_cliente_id = ?";
            jdbcTemplate.update(sqlJuridico,
                    client.getLegalName(), client.getCnpj(), client.getStateRegistration(), id);

            // Atualiza telefones: apaga e insere novamente
            deletePhonesByIdPerson(id);
            if (client.getPhone() != null && !client.getPhone().isEmpty()) {
                for (String numero : client.getPhone()) {
                    if (numero != null && !numero.isBlank()) {
                        String sqlTelefone = "INSERT INTO telefone (numero, id_telefone) VALUES (?, ?)";
                        jdbcTemplate.update(sqlTelefone, numero, id);
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao atualizar cliente jurídico: " + e.getMessage());
        }
    }

    public void deleteById(Integer id) {
        try {
            String sqlJuridico = "DELETE FROM juridico WHERE fk_cliente_id = ?";
            String sqlCliente = "DELETE FROM cliente WHERE id_cliente = ?";
            String sqlPessoa = "DELETE FROM pessoa WHERE id_pessoa = ?";
            String sqlTelefone = "DELETE FROM telefone WHERE id_telefone = ?";

            jdbcTemplate.update(sqlTelefone, id);
            jdbcTemplate.update(sqlJuridico, id);
            jdbcTemplate.update(sqlCliente, id);
            jdbcTemplate.update(sqlPessoa, id);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao deletar cliente jurídico: " + e.getMessage());
        }
    }

    private List<String> findPhonesByIdPerson(Integer idPerson) {
        String sql = "SELECT numero FROM telefone WHERE id_telefone = ?";
        return jdbcTemplate.query(
                sql,
                (rs, rn) -> rs.getString("numero"),
                idPerson
        );
    }

    private void deletePhonesByIdPerson(Integer idPerson) {
        String sql = "DELETE FROM telefone WHERE id_telefone = ?";
        jdbcTemplate.update(sql, idPerson);
    }

    private class LegalClientRowMapper implements RowMapper<LegalClient> {
        @Override
        public LegalClient mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new LegalClient(
                    rs.getInt("id_cliente"),
                    rs.getString("razao_social"),
                    rs.getString("cnpj"),
                    rs.getString("inscr_estadual"),
                    rs.getString("rua"),
                    rs.getString("numero"),
                    rs.getString("bairro"),
                    rs.getString("cidade"),
                    rs.getString("cep"),
                    findPhonesByIdPerson(rs.getInt("id_cliente"))
            );
        }
    }
}