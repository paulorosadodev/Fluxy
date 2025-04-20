package br.com.project.repository;

import br.com.project.model.Physique;
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

    public void save(Physique fisico) {
        String sql = "INSERT INTO fisico (fk_cliente_id, nome, cpf) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql,
                fisico.getFkClienteId(),  // <-- Corrigido aqui!
                fisico.getNome(),
                fisico.getCpf()
        );
    }

    public Optional<Physique> findById(Integer idCliente) {
        String sql = "SELECT * FROM fisico WHERE fk_cliente_id = ?";
        List<Physique> result = jdbcTemplate.query(sql, new FisicoRowMapper(), idCliente);
        return result.stream().findFirst();
    }

    public List<Physique> findAll() {
        String sql = "SELECT * FROM fisico";
        return jdbcTemplate.query(sql, new FisicoRowMapper());
    }

    public void update(Physique fisico) {
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

    private static class FisicoRowMapper implements RowMapper<Physique> {
        @Override
        public Physique mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Physique(
                    rs.getInt("fk_cliente_id"),
                    rs.getString("nome"),
                    rs.getString("cpf")
            );
        }
    }
}
