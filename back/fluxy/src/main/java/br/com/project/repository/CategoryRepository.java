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
        try {
            jdbcTemplate.update(sql, category.getCode(), category.getName());
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public Optional<Category> findByCode(String code) {
        String sql = "SELECT * FROM categoria WHERE codigo = ?";
        try {
            List<Category> result = jdbcTemplate.query(sql, new CategoryRowMapper(), code);
            return result.stream().findFirst();
        } catch (Exception e) {
            throw new RuntimeException(code, e);
        }
    }

    public List<Category> findAll() {
        String sql = "SELECT * FROM categoria";
        try {
            return jdbcTemplate.query(sql, new CategoryRowMapper());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public int getTotalCategoriesCount() {
        try {
            String sql = "SELECT COUNT(*) FROM categoria";
            return jdbcTemplate.queryForObject(sql, Integer.class);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public void update(Category category) {
        String sql = "UPDATE categoria SET nome = ? WHERE codigo = ?";
        try {
            int rows = jdbcTemplate.update(sql, category.getName(), category.getCode());
            if (rows == 0) {
                throw new RuntimeException("Nenhuma categoria foi atualizada. Código: " + category.getCode());
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public void deleteByCode(String code) {
        String sql = "DELETE FROM categoria WHERE codigo = ?";
        try {
            int rows = jdbcTemplate.update(sql, code);
            if (rows == 0) {
                throw new RuntimeException("Nenhuma categoria foi deletada. Código: " + code);
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
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

