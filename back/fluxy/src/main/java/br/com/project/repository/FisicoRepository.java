package br.com.project.repository;

import br.com.project.model.Fisico;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class FisicoRepository {

    private final JdbcTemplate jdbcTemplate;

    public FisicoRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void save(Fisico fisico) {
        String sql = "INSERT INTO fisico (fk_cliente_id, nome, cpf) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql,
                fisico.getFkClienteId(),  // <-- Corrigido aqui!
                fisico.getNome(),
                fisico.getCpf()
        );
    }

    public Optional<Fisico> findById(Integer idCliente) {
        String sql = "SELECT * FROM fisico WHERE fk_cliente_id = ?";
        List<Fisico> result = jdbcTemplate.query(sql, new FisicoRowMapper(), idCliente);
        return result.stream().findFirst();
    }

    public List<Fisico> findAll() {
        String sql = "SELECT * FROM fisico";
        return jdbcTemplate.query(sql, new FisicoRowMapper());
    }

    public void update(Fisico fisico) {
        String sql = "UPDATE fisico SET nome = ?, cpf = ? WHERE fk_cliente_id = ?";
        jdbcTemplate.update(sql,
                fisico.getNome(),
                fisico.getCpf(),
                fisico.getFkClienteId()
        );
    }

    public void deleteById(Integer idCliente) {
        String sql = "DELETE FROM fisico WHERE fk_cliente_id = ?";
        jdbcTemplate.update(sql, idCliente);
    }

    private static class FisicoRowMapper implements RowMapper<Fisico> {
        @Override
        public Fisico mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Fisico(
                    rs.getInt("fk_cliente_id"),
                    rs.getString("nome"),
                    rs.getString("cpf")
            );
        }
    }
}
