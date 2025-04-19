package br.com.project.repository;

import br.com.project.model.Produto;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class ProdutoRepository {

    private final JdbcTemplate jdbcTemplate;

    public ProdutoRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void save(Produto produto) {
        String sql = "INSERT INTO produto (qtd_estoque, cod_ea, preco, descricao, nome) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                produto.getQtdEstoque(),
                produto.getCodEa(),
                produto.getPreco(),
                produto.getDescricao(),
                produto.getNome()
        );
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
        String sql = "UPDATE produto SET qtd_estoque = ?, cod_ea = ?, preco = ?, descricao = ?, nome = ? WHERE id_produto = ?";
        jdbcTemplate.update(sql,
                produto.getQtdEstoque(),
                produto.getCodEa(),
                produto.getPreco(),
                produto.getDescricao(),
                produto.getNome(),
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
                    rs.getInt("preco"),
                    rs.getString("descricao"),
                    rs.getString("nome")
            );
        }
    }
}
