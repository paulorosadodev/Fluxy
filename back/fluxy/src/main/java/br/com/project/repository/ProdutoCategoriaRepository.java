package br.com.project.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ProdutoCategoriaRepository {

    private final JdbcTemplate jdbcTemplate;

    public ProdutoCategoriaRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void save(Integer idProduto, String codigoCategoria) {
        String sql = "INSERT INTO produto_categoria (fk_produto_id, fk_categoria_codigo) VALUES (?, ?)";
        jdbcTemplate.update(sql, idProduto, codigoCategoria);
    }
}
