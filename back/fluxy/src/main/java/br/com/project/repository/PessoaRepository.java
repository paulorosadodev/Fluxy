package br.com.project.repository;

import br.com.project.model.Pessoa;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class PessoaRepository {

    private final JdbcTemplate jdbcTemplate;

    public PessoaRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void save(Pessoa pessoa) {
        String sql = "INSERT INTO pessoa (id_pessoa, rua, numero, bairro, cidade, cep) VALUES (?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, pessoa.getIdPessoa(), pessoa.getRua(), pessoa.getNumero(), pessoa.getBairro(), pessoa.getCidade(), pessoa.getCep());
    }

    public Optional<Pessoa> findById(Integer id) {
        String sql = "SELECT * FROM pessoa WHERE id_pessoa = ?";
        List<Pessoa> result = jdbcTemplate.query(sql, new PessoaRowMapper(), id);
        return result.stream().findFirst();
    }

    public List<Pessoa> findAll() {
        String sql = "SELECT * FROM pessoa";
        return jdbcTemplate.query(sql, new PessoaRowMapper());
    }

    public void update(Pessoa pessoa) {
        String sql = "UPDATE pessoa SET rua = ?, numero = ?, bairro = ?, cidade = ?, cep = ? WHERE id_pessoa = ?";
        jdbcTemplate.update(sql, pessoa.getRua(), pessoa.getNumero(), pessoa.getBairro(), pessoa.getCidade(), pessoa.getCep(), pessoa.getIdPessoa());
    }

    public void deleteById(Integer id) {
        String sql = "DELETE FROM pessoa WHERE id_pessoa = ?";
        jdbcTemplate.update(sql, id);
    }

    private static class PessoaRowMapper implements RowMapper<Pessoa> {
        @Override
        public Pessoa mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Pessoa(
                    rs.getInt("id_pessoa"),
                    rs.getString("rua"),
                    rs.getString("numero"),
                    rs.getString("bairro"),
                    rs.getString("cidade"),
                    rs.getString("cep")
            );
        }
    }
}
