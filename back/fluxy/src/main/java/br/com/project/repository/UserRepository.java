package br.com.project.repository;

import br.com.project.model.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class UserRepository {

    private final JdbcTemplate jdbcTemplate;

    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Optional<User> findByName(String name) {
        String sql = "SELECT * FROM usuario WHERE nome = ?";

        List<User> result = jdbcTemplate.query(sql, new UsuarioRowMapper(), name);

        return result.stream().findFirst();
    }

    public void save(User user) {
        String sql = "INSERT INTO usuario (nome, senha, cargo) VALUES ( ?, ?, ?)";
        jdbcTemplate.update(sql, user.getName(), user.getPassword(), user.getRole());
    }

    private static class UsuarioRowMapper implements RowMapper<User> {
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User();
            user.setName(rs.getString("nome"));
            user.setPassword(rs.getString("senha"));
            user.setRole(rs.getString("cargo"));
            return user;
        }
    }
}