package br.com.project.repository;

import br.com.project.model.Fornecedor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class FornecedorRepository {

    private final JdbcTemplate jdbcTemplate;

    public FornecedorRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void save(Fornecedor fornecedor) {
        String sql = "INSERT INTO fornecedor (id_fornecedor, cnpj, nome) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql,
                fornecedor.getIdPessoa(), // O id_fornecedor é o id_pessoa
                fornecedor.getCnpj(),
                fornecedor.getNome()
        );
    }

    public Optional<Fornecedor> findById(Integer id) {
        String sql = "SELECT * FROM fornecedor WHERE id_fornecedor = ?";
        List<Fornecedor> result = jdbcTemplate.query(sql, new FornecedorRowMapper(), id);
        return result.stream().findFirst();
    }

    public List<Fornecedor> findAll() {
        String sql = "SELECT * FROM fornecedor";
        return jdbcTemplate.query(sql, new FornecedorRowMapper());
    }

    public void update(Fornecedor fornecedor) {
        String sql = "UPDATE fornecedor SET cnpj = ?, nome = ? WHERE id_fornecedor = ?";
        jdbcTemplate.update(sql,
                fornecedor.getCnpj(),
                fornecedor.getNome(),
                fornecedor.getIdFornecedor()
        );
    }

    public void deleteById(Integer id) {
        String sql = "DELETE FROM fornecedor WHERE id_fornecedor = ?";
        jdbcTemplate.update(sql, id);
    }

    private static class FornecedorRowMapper implements RowMapper<Fornecedor> {
        @Override
        public Fornecedor mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Fornecedor(
                    rs.getInt("id_fornecedor"),
                    rs.getInt("id_fornecedor"), // o id_pessoa é o mesmo id_fornecedor
                    rs.getString("cnpj"),
                    rs.getString("nome")
            );
        }
    }
}
