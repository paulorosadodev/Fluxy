package br.com.project.repository;

import br.com.project.model.Produto;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

@Repository
public class ProdutoRepository {

    private final JdbcTemplate jdbcTemplate;

    public ProdutoRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Integer save(Produto produto) {
        String sql = "INSERT INTO produto (qtd_estoque, cod_ea, preco, nome, fk_categoria_codigo) VALUES (?, ?, ?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, produto.getQtdEstoque());
            ps.setString(2, produto.getCodEa());
            ps.setDouble(3, produto.getPreco());
            ps.setString(4, produto.getNome());
            ps.setString(5, produto.getCodigoCategoria());
            return ps;
        }, keyHolder);

        return keyHolder.getKey().intValue();
    }

    public Optional<Produto> findById(Integer id) {
        String sql = "SELECT * FROM produto WHERE id_produto = ?";
        List<Produto> result = jdbcTemplate.query(sql, new ProdutoRowMapper(), id);
        return result.stream().findFirst();
    }

    public List<Produto> findAll() {
        String sql = "SELECT * FROM produto";
        return jdbcTemplate.query(sql, new ProdutoRowMapper());
    }

    public void update(Produto produto) {
        String sql = "UPDATE produto SET qtd_estoque = ?, cod_ea = ?, preco = ?, nome = ?, fk_categoria_codigo = ? WHERE id_produto = ?";
        jdbcTemplate.update(sql,
                produto.getQtdEstoque(),
                produto.getCodEa(),
                produto.getPreco(),
                produto.getNome(),
                produto.getCodigoCategoria(),
                produto.getIdProduto()
        );
    }

    public void deleteById(Integer id) {
        String sql = "DELETE FROM produto WHERE id_produto = ?";
        jdbcTemplate.update(sql, id);
    }

    private static class ProdutoRowMapper implements RowMapper<Produto> {
        @Override
        public Produto mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Produto(
                    rs.getInt("id_produto"),
                    rs.getInt("qtd_estoque"),
                    rs.getString("cod_ea"),
                    rs.getDouble("preco"),
                    rs.getString("nome"),
                    rs.getString("fk_categoria_codigo")
            );
        }
    }
}
