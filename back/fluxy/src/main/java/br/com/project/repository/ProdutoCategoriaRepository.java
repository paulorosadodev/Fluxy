package br.com.project.repository;

import br.com.project.model.ProdutoCategoria;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ProdutoCategoriaRepository {

    private final JdbcTemplate jdbcTemplate;

    public ProdutoCategoriaRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void save(ProdutoCategoria produtoCategoria) {
        String sql = "INSERT INTO produto_categoria (fk_produto_id, fk_categoria_codigo) VALUES (?, ?)";
        jdbcTemplate.update(sql,
                produtoCategoria.getFkProdutoId(),
                produtoCategoria.getFkCategoriaCodigo()
        );
    }

    public List<ProdutoCategoria> findAll() {
        String sql = "SELECT * FROM produto_categoria";
        return jdbcTemplate.query(sql, new ProdutoCategoriaRowMapper());
    }

    public void deleteByIds(Integer produtoId, String categoriaCodigo) {
        String sql = "DELETE FROM produto_categoria WHERE fk_produto_id = ? AND fk_categoria_codigo = ?";
        jdbcTemplate.update(sql, produtoId, categoriaCodigo);
    }

    private static class ProdutoCategoriaRowMapper implements RowMapper<ProdutoCategoria> {
        @Override
        public ProdutoCategoria mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new ProdutoCategoria(
                    rs.getInt("fk_produto_id"),
                    rs.getString("fk_categoria_codigo")
            );
        }
    }
}
