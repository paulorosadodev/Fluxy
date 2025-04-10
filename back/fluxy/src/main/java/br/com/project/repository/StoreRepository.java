package br.com.project.repository;

import br.com.project.model.Store;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class StoreRepository {

    private final JdbcTemplate jdbcTemplate;

    public StoreRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Optional<Store> findByName(String name) {
        String sql = "SELECT * FROM loja WHERE nome = ?";

        List<Store> result = jdbcTemplate.query(sql, new LojaRowMapper(), name);

        return result.stream().findFirst();
    }

    public void save(Store store) {
        String sql = "INSERT INTO loja (nome, senha) VALUES ( ?, ?)";
        jdbcTemplate.update(sql, store.getName(), store.getPassword());
    }

    private static class LojaRowMapper implements RowMapper<Store> {
        @Override
        public Store mapRow(ResultSet rs, int rowNum) throws SQLException {
            Store store = new Store();
            store.setName(rs.getString("nome"));
            store.setPassword(rs.getString("senha"));
            return store;
        }
    }
}