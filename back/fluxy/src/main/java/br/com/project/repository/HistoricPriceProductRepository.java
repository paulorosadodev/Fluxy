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
        try {
            String sql = "INSERT INTO historico_preco_produto (codigo_produto, data, preco) VALUES (?, ?, ?)";
            KeyHolder keyHolder = new GeneratedKeyHolder();

            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setInt(1, historic.getProductId());
                ps.setDate(2, Date.valueOf(historic.getDate()));
                ps.setDouble(3, historic.getPrice());
                return ps;
            }, keyHolder);

            return keyHolder.getKey().intValue();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao salvar histórico de preço do produto: " + e.getMessage());
        }
    }

    public void update(HistoricPriceProduct historic) {
        try {
            String sql = "UPDATE historico_preco_produto SET codigo_produto = ?, data = ?, preco = ? WHERE id_historico_preco_produto = ?";
            jdbcTemplate.update(sql,
                    historic.getProductId(),
                    Date.valueOf(historic.getDate()),
                    historic.getPrice(),
                    historic.getIdHistoricPriceProduct()
            );
        } catch (Exception e) {
            throw new RuntimeException("Erro ao atualizar histórico de preço do produto: " + e.getMessage());
        }
    }

    public Optional<HistoricPriceProduct> findById(Integer id) {
        try {
            String sql = "SELECT * FROM historico_preco_produto WHERE id_historico_preco_produto = ?";
            List<HistoricPriceProduct> result = jdbcTemplate.query(sql, new HistoricPriceProductRowMapper(), id);
            return result.stream().findFirst();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar histórico de preço por ID: " + e.getMessage());
        }
    }

    public List<HistoricPriceProduct> findAll() {
        try {
            String sql = "SELECT * FROM historico_preco_produto";
            return jdbcTemplate.query(sql, new HistoricPriceProductRowMapper());
        } catch (Exception e) {
            throw new RuntimeException("Erro ao listar históricos de preço: " + e.getMessage());
        }
    }

    public void deleteById(Integer id) {
        try {
            String sql = "DELETE FROM historico_preco_produto WHERE id_historico_preco_produto = ?";
            jdbcTemplate.update(sql, id);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao deletar histórico de preço por ID: " + e.getMessage());
        }
    }

    public void deleteByProductId(Integer productId) {
        try {
            String sql = "DELETE FROM historico_preco_produto WHERE codigo_produto = ?";
            jdbcTemplate.update(sql, productId);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao deletar histórico de preço por código do produto: " + e.getMessage());
        }
    }

    private static class HistoricPriceProductRowMapper implements RowMapper<HistoricPriceProduct> {
        @Override
        public HistoricPriceProduct mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new HistoricPriceProduct(
                    rs.getInt("id_historico_preco_produto"),
                    rs.getInt("codigo_produto"),
                    rs.getDate("data").toLocalDate(),
                    rs.getDouble("preco")
            );
        }
    }
}
