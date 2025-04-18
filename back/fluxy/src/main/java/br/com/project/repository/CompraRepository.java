package br.com.project.repository;

import br.com.project.model.Compra;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class CompraRepository {

    private final JdbcTemplate jdbcTemplate;

    public CompraRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void save(Compra compra) {
        String sql = "INSERT INTO compra (data, hora, parcelas, tipo, qtd_produto, fk_produto_id, fk_cliente_id, fk_operacional_id_funcionario) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                compra.getData(),
                compra.getHora(),
                compra.getParcelas(),
                compra.getTipo(),
                compra.getQtdProduto(),
                compra.getIdProduto(),
                compra.getIdCliente(),
                compra.getIdFuncionarioOperacional()
        );
    }

    public Optional<Compra> findByNumero(Integer numero) {
        String sql = "SELECT * FROM compra WHERE numero = ?";
        List<Compra> result = jdbcTemplate.query(sql, new CompraRowMapper(), numero);
        return result.stream().findFirst();
    }

    public List<Compra> findAll() {
        String sql = "SELECT * FROM compra";
        return jdbcTemplate.query(sql, new CompraRowMapper());
    }

    public void update(Compra compra) {
        String sql = "UPDATE compra SET data = ?, hora = ?, parcelas = ?, tipo = ?, qtd_produto = ?, fk_produto_id = ?, fk_cliente_id = ?, fk_operacional_id_funcionario = ? WHERE numero = ?";
        jdbcTemplate.update(sql,
                compra.getData(),
                compra.getHora(),
                compra.getParcelas(),
                compra.getTipo(),
                compra.getQtdProduto(),
                compra.getIdProduto(),
                compra.getIdCliente(),
                compra.getIdFuncionarioOperacional(),
                compra.getNumero()
        );
    }

    public void deleteByNumero(Integer numero) {
        String sql = "DELETE FROM compra WHERE numero = ?";
        jdbcTemplate.update(sql, numero);
    }

    private static class CompraRowMapper implements RowMapper<Compra> {
        @Override
        public Compra mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Compra(
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
