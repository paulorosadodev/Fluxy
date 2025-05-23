package br.com.project.repository;

import br.com.project.dto.response.PaymentTypeCountResponseDTO;
import br.com.project.dto.response.PurchaseCountByMonthAndYearResponseDTO;
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
            ps.setInt(5, purchase.getProductAmount());
            ps.setInt(6, purchase.getProductId());
            ps.setInt(7, purchase.getClientId());
            ps.setInt(8, purchase.getEmployeeId());
            return ps;
        }, keyHolder);

        return keyHolder.getKey().intValue();
    }

    public int countAllPurchases() {
        try {
            String sql = "SELECT COUNT(*) FROM compra";
            Integer total = jdbcTemplate.queryForObject(sql, Integer.class);
            return total != null ? total : 0;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao contar compras: " + e.getMessage());
        }
    }

    public Double sumAllPurchaseCosts() {
        String sql = """
        SELECT SUM(c.qtd_produto * p.preco) 
        FROM compra c
        JOIN produto p ON c.fk_produto_id = p.id_produto
    """;

        Double total = jdbcTemplate.queryForObject(sql, Double.class);
        return total != null ? total : 0.0;
    }

    public Double averagePurchaseCost() {
        String sql = """
        SELECT AVG(c.qtd_produto * p.preco)
        FROM compra c
        JOIN produto p ON c.fk_produto_id = p.id_produto
    """;

        Double average = jdbcTemplate.queryForObject(sql, Double.class);
        return average != null ? average : 0.0;
    }

    public Double averagePurchaseCostByMonthAndYear(int month, int year) {
        String sql = """
        SELECT AVG(p.preco * c.qtd_produto)
        FROM compra c
        JOIN produto p ON c.fk_produto_id = p.id_produto
        WHERE MONTH(c.data) = ? AND YEAR(c.data) = ?
    """;

        Double average = jdbcTemplate.queryForObject(sql, Double.class, month, year);
        return average != null ? average : 0.0;
    }

    public PurchaseCountByMonthAndYearResponseDTO countPurchasesByMonthAndYear(int month, int year) {
        String sql = """
        SELECT COUNT(*) AS quantidade
        FROM compra
        WHERE MONTH(data) = ? AND YEAR(data) = ?
    """;

        Integer count = jdbcTemplate.queryForObject(sql, new Object[]{month, year}, Integer.class);

        return new PurchaseCountByMonthAndYearResponseDTO(count != null ? count : 0);
    }

    public List<PaymentTypeCountResponseDTO> countPurchasesByPaymentType() {
        String sql = """
        SELECT tipo, COUNT(*) AS quantidade
        FROM compra
        GROUP BY tipo
        ORDER BY quantidade DESC
    """;

        return jdbcTemplate.query(sql, (rs, rowNum) ->
                new PaymentTypeCountResponseDTO(
                        rs.getString("tipo"),
                        rs.getInt("quantidade")
                )
        );
    }

    public List<Purchase> findAllOrderedByCostDesc() {
        String sql = """
        SELECT c.*
        FROM compra c
        JOIN produto p ON c.fk_produto_id = p.id_produto
        ORDER BY (p.preco * c.qtd_produto) DESC
        LIMIT 5
    """;
        return jdbcTemplate.query(sql, new PurchaseRowMapper());
    }

    public List<Purchase> findAllOrderedByCostAsc() {
        String sql = """
        SELECT c.*
        FROM compra c
        JOIN produto p ON c.fk_produto_id = p.id_produto
        ORDER BY (p.preco * c.qtd_produto) ASC
        LIMIT 5
    """;
        return jdbcTemplate.query(sql, new PurchaseRowMapper());
    }

    public Double sumPurchaseCostsByMonthAndYear(int month, int year) {
        String sql = """
        SELECT SUM(p.preco * c.qtd_produto)
        FROM compra c
        JOIN produto p ON c.fk_produto_id = p.id_produto
        WHERE MONTH(c.data) = ? AND YEAR(c.data) = ?
    """;
        Double total = jdbcTemplate.queryForObject(sql, Double.class, month, year);
        return total != null ? total : 0.0;
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
                purchase.getProductAmount(),
                purchase.getProductId(),
                purchase.getClientId(),
                purchase.getEmployeeId(),
                purchase.getNumber()
        );
    }

    public void deleteByNumber(Integer number) {

        try {
            String sql = "DELETE FROM compra WHERE numero = ?";
            jdbcTemplate.update(sql, number);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

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
