package br.com.project.repository;

import br.com.project.model.Juridico;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class JuridicoRepository {

    private final JdbcTemplate jdbcTemplate;

    public JuridicoRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void save(Juridico juridico) {
        String sql = "INSERT INTO juridico (fk_cliente_id, inscr_estadual, cnpj, razao_social) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, juridico.getFkClienteId(), juridico.getInscrEstadual(), juridico.getCnpj(), juridico.getRazaoSocial());
    }

    public Optional<Juridico> findById(Integer id) {
        String sql = "SELECT * FROM juridico WHERE fk_cliente_id = ?";
        List<Juridico> result = jdbcTemplate.query(sql, new JuridicoRowMapper(), id);
        return result.stream().findFirst();
    }

    public List<Juridico> findAll() {
        String sql = "SELECT * FROM juridico";
        return jdbcTemplate.query(sql, new JuridicoRowMapper());
    }

    public void update(Juridico juridico) {
        String sql = "UPDATE juridico SET inscr_estadual = ?, cnpj = ?, razao_social = ? WHERE fk_cliente_id = ?";
        jdbcTemplate.update(sql, juridico.getInscrEstadual(), juridico.getCnpj(), juridico.getRazaoSocial(), juridico.getFkClienteId());
    }

    public void deleteById(Integer id) {
        String sql = "DELETE FROM juridico WHERE fk_cliente_id = ?";
        jdbcTemplate.update(sql, id);
    }

    private static class JuridicoRowMapper implements RowMapper<Juridico> {
        @Override
        public Juridico mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Juridico(
                    rs.getInt("fk_cliente_id"),
                    rs.getString("inscr_estadual"),
                    rs.getString("cnpj"),
                    rs.getString("razao_social")
            );
        }
    }
}
