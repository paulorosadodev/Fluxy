package br.com.project.repository;

import br.com.project.model.ProductPriceHistoryLink;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ProductHistoryRepository {

    private final JdbcTemplate jdbcTemplate;

    public ProductHistoryRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void save(ProductPriceHistoryLink link) {
        String sql = "INSERT INTO produto_historico (fk_produto_id, fk_historicoprecoproduto_id) VALUES (?, ?)";
        jdbcTemplate.update(sql,
                link.getProductId(),
                link.getPriceHistoryId()
        );
    }

    public List<ProductPriceHistoryLink> findAll() {
        String sql = "SELECT * FROM produto_historico";
        return jdbcTemplate.query(sql, new ProductHistoryRowMapper());
    }

    public void deleteByProductAndHistory(Integer productId, Integer historyId) {
        String sql = "DELETE FROM produto_historico WHERE fk_produto_id = ? AND fk_historicoprecoproduto_id = ?";
        jdbcTemplate.update(sql, productId, historyId);
    }

    private static class ProductHistoryRowMapper implements RowMapper<ProductPriceHistoryLink> {
        @Override
        public ProductPriceHistoryLink mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new ProductPriceHistoryLink(
                    rs.getInt("fk_produto_id"),
                    rs.getInt("fk_historicoprecoproduto_id")
            );
        }
    }
}
