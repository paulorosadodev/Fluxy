package br.com.project.repository;

import br.com.project.model.Supplier;
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
public class SupplierRepository {

    private final JdbcTemplate jdbcTemplate;

    public SupplierRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Integer savePerson(Supplier supplier) {
        String sql = "INSERT INTO pessoa (rua, numero, bairro, cidade, cep) VALUES (?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, supplier.getStreet());
            ps.setString(2, supplier.getNumber());
            ps.setString(3, supplier.getNeighborhood());
            ps.setString(4, supplier.getCity());
            ps.setString(5, supplier.getZipCode());
            return ps;
        }, keyHolder);

        return keyHolder.getKey().intValue();
    }

    public void saveSupplier(Integer idPessoa, Supplier supplier) {
        String sql = "INSERT INTO fornecedor (id_fornecedor, cnpj, nome) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql,
                idPessoa,
                supplier.getCnpj(),
                supplier.getName()
        );
    }

    public List<Supplier> findAll() {
        String sql = "SELECT p.id_pessoa, p.rua, p.numero, p.bairro, p.cidade, p.cep, f.cnpj, f.nome " +
                "FROM fornecedor f INNER JOIN pessoa p ON f.id_fornecedor = p.id_pessoa";
        return jdbcTemplate.query(sql, supplierRowMapper());
    }

    public Optional<Supplier> findById(Integer id) {
        String sql = "SELECT p.id_pessoa, p.rua, p.numero, p.bairro, p.cidade, p.cep, f.cnpj, f.nome " +
                "FROM fornecedor f INNER JOIN pessoa p ON f.id_fornecedor = p.id_pessoa " +
                "WHERE p.id_pessoa = ?";
        List<Supplier> suppliers = jdbcTemplate.query(sql, supplierRowMapper(), id);
        return suppliers.stream().findFirst();
    }

    public void updatePerson(Integer id, Supplier supplier) {
        String sql = "UPDATE pessoa SET rua = ?, numero = ?, bairro = ?, cidade = ?, cep = ? WHERE id_pessoa = ?";
        jdbcTemplate.update(sql,
                supplier.getStreet(),
                supplier.getNumber(),
                supplier.getNeighborhood(),
                supplier.getCity(),
                supplier.getZipCode(),
                id
        );
    }

    public void updateSupplier(Integer id, Supplier supplier) {
        String sql = "UPDATE fornecedor SET cnpj = ?, nome = ? WHERE id_fornecedor = ?";
        jdbcTemplate.update(sql,
                supplier.getCnpj(),
                supplier.getName(),
                id
        );
    }

    public void deleteSupplier(Integer id) {
        String sql = "DELETE FROM fornecedor WHERE id_fornecedor = ?";
        jdbcTemplate.update(sql, id);
    }

    public void deletePerson(Integer id) {
        String sql = "DELETE FROM pessoa WHERE id_pessoa = ?";
        jdbcTemplate.update(sql, id);
    }

    private RowMapper<Supplier> supplierRowMapper() {
        return (ResultSet rs, int rowNum) -> new Supplier(
                rs.getInt("id_pessoa"),
                rs.getString("cnpj"),
                rs.getString("nome"),
                rs.getString("rua"),
                rs.getString("numero"),
                rs.getString("bairro"),
                rs.getString("cidade"),
                rs.getString("cep")
        );
    }
}
