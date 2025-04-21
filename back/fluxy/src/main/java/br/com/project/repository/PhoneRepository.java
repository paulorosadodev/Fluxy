package br.com.project.repository;

import br.com.project.model.Phone;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class PhoneRepository {

    private final JdbcTemplate jdbcTemplate;

    public PhoneRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void save(Phone phone) {
        String sql = "INSERT INTO telefone (id_telefone, numero) VALUES (?, ?)";
        jdbcTemplate.update(sql,
                phone.getIdPerson(),  // Aqui é o id da pessoa (preenchendo o id_telefone como FK para pessoa)
                phone.getNumber()
        );
    }

    public void deleteByIdPerson(Integer idPerson) {
        String sql = "DELETE FROM telefone WHERE id_telefone = ?";
        jdbcTemplate.update(sql, idPerson);
    }

    public void deleteByNumero(String number) {
        String sql = "DELETE FROM telefone WHERE numero = ?";
        jdbcTemplate.update(sql, number);
    }

    public List<Phone> findAll() {
        String sql = "SELECT * FROM telefone";
        return jdbcTemplate.query(sql, new PhoneRowMapper());
    }

    private static class PhoneRowMapper implements RowMapper<Phone> {
        @Override
        public Phone mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Phone(
                    rs.getInt("id_telefone"), // Aqui você preenche o id da pessoa
                    rs.getInt("id_telefone"), // (id_pessoa == id_telefone nesse mapeamento meio "incomum" que você fez)
                    rs.getString("numero")
            );
        }
    }
}
