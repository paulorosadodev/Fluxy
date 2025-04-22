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
import java.util.List;
import java.util.Optional;

@Repository
public class PhysicalClientRepository {

    private final JdbcTemplate jdbcTemplate;

    public PhysicalClientRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Integer save(PhysicalClient client) {
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
    }

    public boolean existsByCpf(String cpf) {
        String sql = "SELECT COUNT(*) FROM fisico WHERE cpf = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, cpf);
        return count != null && count > 0;
    }

    public Optional<PhysicalClient> findById(Integer id) {
        String sql = """
            SELECT 
                c.id_cliente,
                f.nome,
                f.cpf,
                p.rua,
                p.numero,
                p.bairro,
                p.cidade,
                p.cep
            FROM fisico f
            INNER JOIN cliente c ON f.fk_cliente_id = c.id_cliente
            INNER JOIN pessoa p ON c.id_cliente = p.id_pessoa
            WHERE c.id_cliente = ?
        """;

        List<PhysicalClient> result = jdbcTemplate.query(sql, new PhysicalClientRowMapper(), id);
        return result.stream().findFirst();
    }

    public List<PhysicalClient> findAll() {
        String sql = """
            SELECT 
                c.id_cliente,
                f.nome,
                f.cpf,
                p.rua,
                p.numero,
                p.bairro,
                p.cidade,
                p.cep
            FROM fisico f
            INNER JOIN cliente c ON f.fk_cliente_id = c.id_cliente
            INNER JOIN pessoa p ON c.id_cliente = p.id_pessoa
        """;

        return jdbcTemplate.query(sql, new PhysicalClientRowMapper());
    }

    private void deletePhoneByIdPerson(Integer idPerson) {
        String sql = "DELETE FROM telefone WHERE id_telefone = ?";
        jdbcTemplate.update(sql, idPerson);
    }

    private void insertPhone(Integer idPerson, List<String> phones) {
        if (phones != null && !phones.isEmpty()) {
            for (String numero : phones) {
                if (numero != null && !numero.isBlank()) {
                    String sqlTelefone = "INSERT INTO telefone (numero, id_telefone) VALUES (?, ?)";
                    jdbcTemplate.update(sqlTelefone, numero.trim(), idPerson);
                }
            }
        }
    }
    public void update(Integer id, PhysicalClient client) {
        // Atualiza pessoa
        String sqlPessoa = "UPDATE pessoa SET rua = ?, numero = ?, bairro = ?, cidade = ?, cep = ? WHERE id_pessoa = ?";
        jdbcTemplate.update(sqlPessoa,
                client.getStreet(), client.getNumber(), client.getNeighborhood(), client.getCity(),
                client.getCep(), id);

        // Atualiza cliente físico
        String sqlFisico = "UPDATE fisico SET nome = ?, cpf = ? WHERE fk_cliente_id = ?";
        jdbcTemplate.update(sqlFisico,
                client.getName(), client.getCpf(), id);

        // Atualiza telefones
        deletePhoneByIdPerson(id);
        insertPhone(id, client.getPhone());
    }



    public void deleteById(Integer id) {
        String sqlFisico = "DELETE FROM fisico WHERE fk_cliente_id = ?";
        String sqlCliente = "DELETE FROM cliente WHERE id_cliente = ?";
        String sqlPessoa = "DELETE FROM pessoa WHERE id_pessoa = ?";
        String sqlTelefone = "DELETE FROM telefone WHERE id_telefone = ?";

        jdbcTemplate.update(sqlTelefone, id);
        jdbcTemplate.update(sqlFisico, id);
        jdbcTemplate.update(sqlCliente, id);
        jdbcTemplate.update(sqlPessoa, id);
    }

    private List<String> findPhoneByIdPerson(Integer idPerson) {
        String sql = "SELECT numero FROM telefone WHERE id_telefone = ?";
        return jdbcTemplate.query(
                sql,
                (rs, rn) -> rs.getString("numero"),
                idPerson
        );
    }



    private class PhysicalClientRowMapper implements RowMapper<PhysicalClient> {
        @Override
        public PhysicalClient mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new PhysicalClient(
                    rs.getInt("id_cliente"),
                    rs.getString("nome"),
                    rs.getString("cpf"),
                    rs.getString("rua"),
                    rs.getString("numero"),
                    rs.getString("bairro"),
                    rs.getString("cidade"),
                    rs.getString("cep"),
                    findPhoneByIdPerson(rs.getInt("id_cliente")) // Telefones do cliente
            );
        }
    }
}
