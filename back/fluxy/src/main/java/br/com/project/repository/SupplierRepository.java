package br.com.project.repository;

import br.com.project.model.Supplier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class SupplierRepository {

    private final JdbcTemplate jdbcTemplate;

    public SupplierRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void save(Supplier supplier) {
        String sql = "INSERT INTO fornecedor (fornecedor.id, cnpj, nome) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql,
                supplier.getIdPerson(), // O id_fornecedor é o id_pessoa
                supplier.getCnpj(),
                supplier.getName()
        );
    }

    public Optional<Supplier> findById(Integer id) {
        String sql = "SELECT * FROM fornecedor WHERE fornecedor.id = ?";
        List<Supplier> result = jdbcTemplate.query(sql, new FornecedorRowMapper(), id);
        return result.stream().findFirst();
    }

    public List<Supplier> findAll() {
        String sql = "SELECT * FROM fornecedor";
        return jdbcTemplate.query(sql, new FornecedorRowMapper());
    }

    public void update(Supplier supplier) {
        String sql = "UPDATE fornecedor SET cnpj = ?, nome = ? WHERE fornecedor.id = ?";
        jdbcTemplate.update(sql,
                supplier.getCnpj(),
                supplier.getName(),
                supplier.getIdSupplier()
        );
    }

    public void deleteById(Integer id) {
        String sql = "DELETE FROM fornecedor WHERE fornecedor.id = ?";
        jdbcTemplate.update(sql, id);
    }

    private static class FornecedorRowMapper implements RowMapper<Supplier> {
        @Override
        public Supplier mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Supplier(
                    rs.getInt("id_fornecedor"),
                    rs.getInt("id_fornecedor"), // o id_pessoa é o mesmo id_fornecedor
                    rs.getString("cnpj"),
                    rs.getString("nome")
            );
        }
    }
}
