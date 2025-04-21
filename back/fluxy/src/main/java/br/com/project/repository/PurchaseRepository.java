package br.com.project.repository;

import br.com.project.model.Purchase;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

@Repository
public class PurchaseRepository {

    private final JdbcTemplate jdbcTemplate;

    public PurchaseRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Integer save(Purchase purchase) {
        String sql = "INSERT INTO compra (data, hora, parcelas, tipo, qtd_produto, fk_produto_id, fk_cliente_id, fk_operacional_id_funcionario) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setDate(1, java.sql.Date.valueOf(purchase.getDate()));
            ps.setTime(2, java.sql.Time.valueOf(purchase.getTime()));
            ps.setInt(3, purchase.getInstallments());
            ps.setString(4, purchase.getPaymentType());
            ps.setInt(5, purchase.getProductQuantity());
            ps.setInt(6, purchase.getProductId());
            ps.setInt(7, purchase.getClientId());
            ps.setInt(8, purchase.getOperationalEmployeeId());
            return ps;
        }, keyHolder);

        return keyHolder.getKey().intValue();
    }

    public Optional<Purchase> findByNumber(Integer number) {
        String sql = "SELECT * FROM compra WHERE numero = ?";
        List<Purchase> result = jdbcTemplate.query(sql, new PurchaseRowMapper(), number);
        return result.stream().findFirst();
    }

    public List<Purchase> findAll() {
        String sql = "SELECT * FROM compra";
        return jdbcTemplate.query(sql, new PurchaseRowMapper());
    }

    public void update(Purchase purchase) {
        String sql = "UPDATE compra SET data = ?, hora = ?, parcelas = ?, tipo = ?, qtd_produto = ?, fk_produto_id = ?, fk_cliente_id = ?, fk_operacional_id_funcionario = ? " +
                "WHERE numero = ?";
        jdbcTemplate.update(sql,
                java.sql.Date.valueOf(purchase.getDate()),
                java.sql.Time.valueOf(purchase.getTime()),
                purchase.getInstallments(),
                purchase.getPaymentType(),
                purchase.getProductQuantity(),
                purchase.getProductId(),
                purchase.getClientId(),
                purchase.getOperationalEmployeeId(),
                purchase.getNumber()
        );
    }

    public void deleteByNumber(Integer number) {
        String sql = "DELETE FROM compra WHERE numero = ?";
        jdbcTemplate.update(sql, number);
    }

    private static class PurchaseRowMapper implements RowMapper<Purchase> {
        @Override
        public Purchase mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Purchase(
                    rs.getInt("numero"),
                    rs.getDate("data").toLocalDate(),
                    rs.getTime("hora").toLocalTime(),
                    rs.getInt("parcelas"),
                    rs.getString("tipo"),
                    rs.getInt("qtd_produto"),
                    rs.getInt("fk_produto_id"),
                    rs.getInt("fk_cliente_id"),
                    rs.getInt("fk_operacional_id_funcionario")
            );
        }
    }
}
