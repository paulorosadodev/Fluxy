package br.com.project.repository;

import br.com.project.model.Phone;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class TelefoneRepository {

    private final JdbcTemplate jdbcTemplate;

    public TelefoneRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void save(Phone telefone) {
        String sql = "INSERT INTO telefone (numero, id_telefone) VALUES (?, ?)";
        jdbcTemplate.update(sql,
                telefone.getNumero(),
                telefone.getIdTelefone()
        );
    }

    public Optional<Phone> findByNumero(String numero) {
        String sql = "SELECT * FROM telefone WHERE numero = ?";
        List<Phone> result = jdbcTemplate.query(sql, new TelefoneRowMapper(), numero);
        return result.stream().findFirst();
    }

    public List<Phone> findAll() {
        String sql = "SELECT * FROM telefone";
        return jdbcTemplate.query(sql, new TelefoneRowMapper());
    }

    public void update(Phone telefone) {
        String sql = "UPDATE telefone SET id_telefone = ? WHERE numero = ?";
        jdbcTemplate.update(sql,
                telefone.getIdTelefone(),
                telefone.getNumero()
        );
    }

    public void deleteByNumero(String numero) {
        String sql = "DELETE FROM telefone WHERE numero = ?";
        jdbcTemplate.update(sql, numero);
    }

    private static class TelefoneRowMapper implements RowMapper<Phone> {
        @Override
        public Phone mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Phone(
                    rs.getString("numero"),
                    rs.getInt("id_telefone")
            );
        }
    }
}
