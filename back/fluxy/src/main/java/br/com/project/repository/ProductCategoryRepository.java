package br.com.project.repository;

import br.com.project.model.ProductCategory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ProductCategoryRepository {

    private final JdbcTemplate jdbcTemplate;

    public ProductCategoryRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void save(ProductCategory productCategory) {
        String sql = "INSERT INTO produto_categoria (fk_produto_id, fk_categoria_codigo) VALUES (?, ?)";
        jdbcTemplate.update(sql,
                productCategory.getProductId(),
                productCategory.getCategoryCode()
        );
    }

    public void deleteByProductId(Integer productId) {
        String sql = "DELETE FROM produto_categoria WHERE fk_produto_id = ?";
        jdbcTemplate.update(sql, productId);
    }
}
