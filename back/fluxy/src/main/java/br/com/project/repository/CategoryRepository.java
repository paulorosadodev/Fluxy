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
public class CategoryRepository {

    private final JdbcTemplate jdbcTemplate;

    public CategoryRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void save(Category category) {
        String sql = "INSERT INTO categoria (codigo, nome) VALUES (?, ?)";
        jdbcTemplate.update(sql, category.getCode(), category.getName());
    }

    public Optional<Category> findByCode(String code) {
        String sql = "SELECT * FROM categoria WHERE codigo = ?";
        List<Category> result = jdbcTemplate.query(sql, new CategoryRowMapper(), code);
        return result.stream().findFirst();
    }

    public List<Category> findAll() {
        String sql = "SELECT * FROM categoria";
        return jdbcTemplate.query(sql, new CategoryRowMapper());
    }

    public void update(Category category) {
        String sql = "UPDATE categoria SET nome = ? WHERE codigo = ?";
        jdbcTemplate.update(sql, category.getName(), category.getCode());
    }

    public void deleteByCode(String code) {
        String sql = "DELETE FROM categoria WHERE codigo = ?";
        jdbcTemplate.update(sql, code);
    }

    private static class CategoryRowMapper implements RowMapper<Category> {
        @Override
        public Category mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Category(
                    rs.getString("codigo"),
                    rs.getString("nome")
            );
        }
    }
}
