package br.com.project.service;

import br.com.project.model.ProductCategory;
import br.com.project.repository.ProductCategoryRepository;
import org.springframework.stereotype.Service;

@Service
public class ProductCategoryService {

    private final ProductCategoryRepository productCategoryRepository;

    public ProductCategoryService(ProductCategoryRepository productCategoryRepository) {
        this.productCategoryRepository = productCategoryRepository;
    }

    public void save(Integer productId, String categoryCode) {
        ProductCategory productCategory = new ProductCategory(productId, categoryCode);
        productCategoryRepository.save(productCategory);
    }
}
