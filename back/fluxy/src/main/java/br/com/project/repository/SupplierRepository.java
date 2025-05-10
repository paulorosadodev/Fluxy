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
        try {
            String sql = "INSERT INTO pessoa (rua, numero, bairro, cidade, cep) VALUES (?, ?, ?, ?, ?)";
            KeyHolder keyHolder = new GeneratedKeyHolder();

            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, supplier.getStreet());
                ps.setString(2, supplier.getNumber());
                ps.setString(3, supplier.getNeighborhood());
                ps.setString(4, supplier.getCity());
                ps.setString(5, supplier.getCep());
                return ps;
            }, keyHolder);

            return keyHolder.getKey().intValue();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao salvar dados da pessoa: " + e.getMessage());
        }
    }

    public void saveSupplier(Integer idPessoa, Supplier supplier) {
        try {
            String sql = "INSERT INTO fornecedor (id_fornecedor, cnpj, nome) VALUES (?, ?, ?)";
            jdbcTemplate.update(sql,
                    idPessoa,
                    supplier.getCnpj(),
                    supplier.getName()
            );
        } catch (Exception e) {
            throw new RuntimeException("Erro ao salvar fornecedor: " + e.getMessage());
        }
    }

    public List<Supplier> findAll() {
        try {
            String sql = "SELECT p.id_pessoa, p.rua, p.numero, p.bairro, p.cidade, p.cep, f.cnpj, f.nome " +
                    "FROM fornecedor f INNER JOIN pessoa p ON f.id_fornecedor = p.id_pessoa";
            return jdbcTemplate.query(sql, supplierRowMapper());
        } catch (Exception e) {
            throw new RuntimeException("Erro ao listar fornecedores: " + e.getMessage());
        }
    }

    public Optional<Supplier> findById(Integer id) {
        try {
            String sql = "SELECT p.id_pessoa, p.rua, p.numero, p.bairro, p.cidade, p.cep, f.cnpj, f.nome " +
                    "FROM fornecedor f INNER JOIN pessoa p ON f.id_fornecedor = p.id_pessoa " +
                    "WHERE p.id_pessoa = ?";
            List<Supplier> suppliers = jdbcTemplate.query(sql, supplierRowMapper(), id);
            return suppliers.stream().findFirst();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar fornecedor por ID: " + e.getMessage());
        }
    }

    public void updatePerson(Integer id, Supplier supplier) {
        try {
            String sql = "UPDATE pessoa SET rua = ?, numero = ?, bairro = ?, cidade = ?, cep = ? WHERE id_pessoa = ?";
            jdbcTemplate.update(sql,
                    supplier.getStreet(),
                    supplier.getNumber(),
                    supplier.getNeighborhood(),
                    supplier.getCity(),
                    supplier.getCep(),
                    id
            );
        } catch (Exception e) {
            throw new RuntimeException("Erro ao atualizar dados da pessoa: " + e.getMessage());
        }
    }

    public void updateSupplier(Integer id, Supplier supplier) {
        try {
            String sql = "UPDATE fornecedor SET cnpj = ?, nome = ? WHERE id_fornecedor = ?";
            jdbcTemplate.update(sql,
                    supplier.getCnpj(),
                    supplier.getName(),
                    id
            );
        } catch (Exception e) {
            throw new RuntimeException("Erro ao atualizar fornecedor: " + e.getMessage());
        }
    }

    public void deleteSupplier(Integer id) {
        try {
            String sql = "DELETE FROM fornecedor WHERE id_fornecedor = ?";
            jdbcTemplate.update(sql, id);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao deletar fornecedor: " + e.getMessage());
        }
    }

    public void deletePerson(Integer id) {
        try {
            String sql = "DELETE FROM pessoa WHERE id_pessoa = ?";
            jdbcTemplate.update(sql, id);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao deletar pessoa: " + e.getMessage());
        }
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
