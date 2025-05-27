package br.com.project.repository;

import br.com.project.model.Phone;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class PhoneRepository {

    private final JdbcTemplate jdbcTemplate;

    public PhoneRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void save(Phone phone) {
        try {
            if (phone.getNumber() != null && !phone.getNumber().isBlank()) {
                String sql = "INSERT INTO telefone (numero, id_telefone) VALUES (?, ?)";
                jdbcTemplate.update(sql, phone.getNumber(), phone.getIdPerson());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void update(Integer idPerson, String oldNumber, String newNumber) {
        String sql = "UPDATE telefone SET numero = ? WHERE numero = ? AND id_telefone = ?";
        jdbcTemplate.update(sql, newNumber, oldNumber, idPerson);
    }

    public void deleteByIdPerson(Integer idPerson) {
        String sql = "DELETE FROM telefone WHERE id_telefone = ?";
        jdbcTemplate.update(sql, idPerson);
    }

    public void deleteByNumber(String number) {
        String sql = "DELETE FROM telefone WHERE numero = ?";
        jdbcTemplate.update(sql, number);
    }

    public List<Phone> findAll() {
        String sql = "SELECT id_telefone, numero FROM telefone";
        return jdbcTemplate.query(sql, new PhoneRowMapper());
    }

    public List<String> findByPersonId(Integer idPerson) {
        String sql = "SELECT numero FROM telefone WHERE id_telefone = ?";
        return jdbcTemplate.query(
                sql,
                (rs, rowNum) -> rs.getString("numero"),
                idPerson
        );
    }

    public boolean existsByPhone(List<String> phones) {
        if (phones == null || phones.isEmpty()) return false;

        String sql = "SELECT COUNT(*) FROM telefone WHERE numero IN (%s)"
                .formatted(phones.stream().map(p -> "?").collect(Collectors.joining(",")));

        Object[] params = phones.toArray();
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, params);
        return count != null && count > 0;
    }

    private static class PhoneRowMapper implements RowMapper<Phone> {
        @Override
        public Phone mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Phone(
                    rs.getInt("id_telefone"),
                    rs.getString("numero")
            );
        }
    }
}
