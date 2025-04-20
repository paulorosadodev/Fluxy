package br.com.project.repository;

import br.com.project.model.HistoricPriceProduct;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

@Repository
public class HistoricPriceProductRepository {

    private final JdbcTemplate jdbcTemplate;

    public HistoricPriceProductRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Integer save(HistoricPriceProduct historic) {
        String sql = "INSERT INTO historico_preco_produto (data, preco) VALUES (?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setDate(1, Date.valueOf(historic.getDate()));
            ps.setDouble(2, historic.getPrice());
            return ps;
        }, keyHolder);

        return keyHolder.getKey().intValue(); // Retorna o ID gerado
    }

    public void update(HistoricPriceProduct historic) {
        String sql = "UPDATE historico_preco_produto SET data = ?, preco = ? WHERE id_historico_preco_produto = ?";
        jdbcTemplate.update(sql,
                Date.valueOf(historic.getDate()),
                historic.getPrice(),
                historic.getIdHistoricPriceProduct()
        );
    }

    public Optional<HistoricPriceProduct> findById(Integer id) {
        String sql = "SELECT * FROM historico_preco_produto WHERE id_historico_preco_produto = ?";
        List<HistoricPriceProduct> result = jdbcTemplate.query(sql, new HistoricPriceProductRowMapper(), id);
        return result.stream().findFirst();
    }

    public List<HistoricPriceProduct> findAll() {
        String sql = "SELECT * FROM historico_preco_produto";
        return jdbcTemplate.query(sql, new HistoricPriceProductRowMapper());
    }

    public void deleteById(Integer id) {
        String sql = "DELETE FROM historico_preco_produto WHERE id_historico_preco_produto = ?";
        jdbcTemplate.update(sql, id);
    }

    private static class HistoricPriceProductRowMapper implements RowMapper<HistoricPriceProduct> {
        @Override
        public HistoricPriceProduct mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new HistoricPriceProduct(
                    rs.getInt("id_historico_preco_produto"),
                    rs.getDate("data").toLocalDate(),
                    rs.getDouble("preco")
            );
        }
    }
}
