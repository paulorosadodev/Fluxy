package br.com.project.repository;

import br.com.project.dto.response.SupplierDeliveryCountDTO;
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
import java.util.ArrayList;
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
            throw new RuntimeException(e.getMessage());
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

            insertPhone(idPessoa, supplier.getPhone());

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public int countSuppliers() {
        try {
            String sql = "SELECT COUNT(*) FROM fornecedor";
            Integer count = jdbcTemplate.queryForObject(sql, Integer.class);
            return count != null ? count : 0;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public List<SupplierDeliveryCountDTO> findSuppliersByDeliveryCountDesc() {
        String sql = """
        SELECT f.id_fornecedor, f.nome, f.cnpj, COUNT(e.fk_fornecedor_id) AS total_entregas
        FROM fornecedor f
        JOIN entrega e ON f.id_fornecedor = e.fk_fornecedor_id
        GROUP BY f.id_fornecedor, f.nome, f.cnpj
        ORDER BY total_entregas DESC
    """;

        return jdbcTemplate.query(sql, (rs, rowNum) -> new SupplierDeliveryCountDTO(
                rs.getInt("id_fornecedor"),
                rs.getString("nome"),
                rs.getString("cnpj"),
                rs.getInt("total_entregas")
        ));
    }

    public List<SupplierDeliveryCountDTO> findSuppliersByDeliveryCountAsc() {
        String sql = """
        SELECT f.id_fornecedor, f.nome, f.cnpj, COUNT(e.fk_fornecedor_id) AS total_entregas
        FROM fornecedor f
        LEFT JOIN entrega e ON f.id_fornecedor = e.fk_fornecedor_id
        GROUP BY f.id_fornecedor, f.nome, f.cnpj
        ORDER BY total_entregas ASC
    """;

        return jdbcTemplate.query(sql, (rs, rowNum) -> new SupplierDeliveryCountDTO(
                rs.getInt("id_fornecedor"),
                rs.getString("nome"),
                rs.getString("cnpj"),
                rs.getInt("total_entregas")
        ));
    }


    public List<Supplier> findAll() {
        try {
            String sql = "SELECT p.id_pessoa, p.rua, p.numero, p.bairro, p.cidade, p.cep, f.cnpj, f.nome " +
                    "FROM fornecedor f INNER JOIN pessoa p ON f.id_fornecedor = p.id_pessoa";
            return jdbcTemplate.query(sql, supplierRowMapper());
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
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
            throw new RuntimeException(e.getMessage());
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
            throw new RuntimeException(e.getMessage());
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

            deletePhoneByIdPerson(id);
            insertPhone(id, supplier.getPhone());

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public void deleteSupplier(Integer id) {
        try {
            deletePhoneByIdPerson(id);
            String sql = "DELETE FROM fornecedor WHERE id_fornecedor = ?";
            jdbcTemplate.update(sql, id);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public void deletePerson(Integer id) {
        try {
            String sql = "DELETE FROM pessoa WHERE id_pessoa = ?";
            jdbcTemplate.update(sql, id);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private RowMapper<Supplier> supplierRowMapper() {
        return (ResultSet rs, int rowNum) -> {
            Supplier supplier = new Supplier(
                    rs.getInt("id_pessoa"),
                    rs.getString("cnpj"),
                    rs.getString("nome"),
                    rs.getString("rua"),
                    rs.getString("numero"),
                    rs.getString("bairro"),
                    rs.getString("cidade"),
                    rs.getString("cep"),
                    new ArrayList<>()
            );
            supplier.setPhone(findPhoneByIdPerson(supplier.getId()));
            return supplier;
        };
    }

    public boolean existsByCnpj(String cnpj) {
        try {
            String sql = "SELECT COUNT(*) FROM fornecedor WHERE cnpj = ?";
            Integer count = jdbcTemplate.queryForObject(sql, Integer.class, cnpj);
            return count != null && count > 0;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private void insertPhone(Integer idPerson, List<String> phones) {
        if (phones != null && !phones.isEmpty()) {
            for (String numero : phones) {
                if (numero != null && !numero.isBlank()) {
                    String sql = "INSERT INTO telefone (numero, id_telefone) VALUES (?, ?)";
                    jdbcTemplate.update(sql, numero.trim(), idPerson);
                }
            }
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
            throw new RuntimeException(e.getMessage());
        }
    }

    private void deletePhoneByIdPerson(Integer idPerson) {
        try {
            String sql = "DELETE FROM telefone WHERE id_telefone = ?";
            jdbcTemplate.update(sql, idPerson);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public Integer findSupplierIdByCnpj(String cnpj) {
        try {
            cnpj = cnpj.replaceAll("[^\\d]", "");

            if (cnpj.length() != 14) {
                throw new IllegalArgumentException("CNPJ inválido");
            }

            String sql = "SELECT f.id_fornecedor " +
                    "FROM fornecedor f " +
                    "JOIN pessoa p ON p.id_pessoa = f.id_fornecedor " +
                    "WHERE f.cnpj = ?";

            Integer id = jdbcTemplate.queryForObject(sql, new Object[]{ cnpj }, Integer.class);
            return id != null ? id : -1;

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}