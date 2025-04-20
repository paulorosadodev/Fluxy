package br.com.project.repository;

import br.com.project.model.Category;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class CategoriaRepository {

    private final JdbcTemplate jdbcTemplate;

    public CategoriaRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void save(Category categoria) {
        String sql = "INSERT INTO categoria (codigo, nome) VALUES (?, ?)";
        jdbcTemplate.update(sql,
                categoria.getCodigo(),
                categoria.getNome()
        );
    }

    public Optional<Category> findByCodigo(String codigo) {
        String sql = "SELECT * FROM categoria WHERE codigo = ?";
        List<Category> result = jdbcTemplate.query(sql, new CategoriaRowMapper(), codigo);
        return result.stream().findFirst();
    }

    public List<Category> findAll() {
        String sql = "SELECT * FROM categoria";
        return jdbcTemplate.query(sql, new CategoriaRowMapper());
    }

    public void update(Category categoria) {
        String sql = "UPDATE categoria SET nome = ? WHERE codigo = ?";
        jdbcTemplate.update(sql,
                categoria.getNome(),
                categoria.getCodigo()
        );
    }

    public void deleteByCodigo(String codigo) {
        String sql = "DELETE FROM categoria WHERE codigo = ?";
        jdbcTemplate.update(sql, codigo);
    }

    private static class CategoriaRowMapper implements RowMapper<Category> {
        @Override
        public Category mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Category(
                    rs.getString("codigo"),
                    rs.getString("nome")
            );
        }
    }
}
