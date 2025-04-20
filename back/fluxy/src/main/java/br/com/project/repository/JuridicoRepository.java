package br.com.project.repository;

import br.com.project.model.Juridical;
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

    public void save(Juridical juridico) {
        String sql = "INSERT INTO juridico (fk_cliente_id, inscr_estadual, cnpj, razao_social) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                juridico.getFkClienteId(),
                juridico.getInscrEstadual(),
                juridico.getCnpj(),
                juridico.getRazaoSocial()
        );
    }

    public Optional<Juridical> findByFkClienteId(Integer fkClienteId) {
        String sql = "SELECT * FROM juridico WHERE fk_cliente_id = ?";
        List<Juridical> result = jdbcTemplate.query(sql, new JuridicoRowMapper(), fkClienteId);
        return result.stream().findFirst();
    }

    public List<Juridical> findAll() {
        String sql = "SELECT * FROM juridico";
        return jdbcTemplate.query(sql, new JuridicoRowMapper());
    }

    public void update(Juridical juridico) {
        String sql = "UPDATE juridico SET inscr_estadual = ?, cnpj = ?, razao_social = ? WHERE fk_cliente_id = ?";
        jdbcTemplate.update(sql,
                juridico.getInscrEstadual(),
                juridico.getCnpj(),
                juridico.getRazaoSocial(),
                juridico.getFkClienteId()
        );
    }

    public void deleteByFkClienteId(Integer fkClienteId) {
        String sql = "DELETE FROM juridico WHERE fk_cliente_id = ?";
        jdbcTemplate.update(sql, fkClienteId);
    }

    private static class JuridicoRowMapper implements RowMapper<Juridical> {
        @Override
        public Juridical mapRow(ResultSet rs, int rowNum) throws SQLException {
            Juridical juridico = new Juridical();
            juridico.setFkClienteId(rs.getInt("fk_cliente_id"));
            juridico.setInscrEstadual(rs.getString("inscr_estadual"));
            juridico.setCnpj(rs.getString("cnpj"));
            juridico.setRazaoSocial(rs.getString("razao_social"));
            return juridico;
        }
    }
}
