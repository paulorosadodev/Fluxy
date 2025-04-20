package br.com.project.repository;

import br.com.project.model.Purchase;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class PurchaseRepository {

    private final JdbcTemplate jdbcTemplate;

    public PurchaseRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void save(Purchase purchase) {
        String sql = "INSERT INTO compra (data, hora, parcelas, tipo, qtd_produto, fk_produto_id, fk_cliente_id, fk_operacional_matricula) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                purchase.getDate(),
                purchase.getHour(),
                purchase.getInstallments(),
                purchase.getType(),
                purchase.getProductAmount(),
                purchase.getIdProduct(),
                purchase.getIdClient(),
                purchase.getIdEmployeeOperational()
        );
    }

    public Optional<Purchase> findByNumber(Integer number) {
        String sql = "SELECT * FROM compra WHERE numero = ?";
        List<Purchase> result = jdbcTemplate.query(sql, new CompraRowMapper(), number);
        return result.stream().findFirst();
    }

    public List<Purchase> findAll() {
        String sql = "SELECT * FROM compra";
        return jdbcTemplate.query(sql, new CompraRowMapper());
    }

    public void update(Purchase purchase) {
        String sql = "UPDATE compra SET data = ?, hora = ?, parcelas = ?, tipo = ?, qtd_produto = ?, fk_produto_id = ?, fk_cliente_id = ?, fk_operacional_matricula = ? WHERE numero = ?";
        jdbcTemplate.update(sql,
                purchase.getDate(),
                purchase.getHour(),
                purchase.getInstallments(),
                purchase.getType(),
                purchase.getProductAmount(),
                purchase.getIdProduct(),
                purchase.getIdClient(),
                purchase.getIdEmployeeOperational(),
                purchase.getNumber()
        );
    }

    public void deleteByNumero(Integer numero) {
        String sql = "DELETE FROM compra WHERE numero = ?";
        jdbcTemplate.update(sql, numero);
    }

    private static class CompraRowMapper implements RowMapper<Purchase> {
        @Override
        public Purchase mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Purchase(
                    rs.getInt("numero"),
                    rs.getString("data"),
                    rs.getString("hora"),
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
