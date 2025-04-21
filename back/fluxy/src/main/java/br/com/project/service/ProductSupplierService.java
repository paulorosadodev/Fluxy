package br.com.project.service;

import br.com.project.model.ProductSupplier;
import br.com.project.repository.ProductSupplierRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductSupplierService {

    private final ProductSupplierRepository repository;

    public ProductSupplierService(ProductSupplierRepository repository) {
        this.repository = repository;
    }

    public void save(ProductSupplier link) {
        repository.save(link);
    }

    public List<ProductSupplier> findAll() {
        return repository.findAll();
    }

    public void deleteBySupplierAndProduct(Integer supplierId, Integer productId) {
        repository.deleteBySupplierAndProduct(supplierId, productId);
    }
}
