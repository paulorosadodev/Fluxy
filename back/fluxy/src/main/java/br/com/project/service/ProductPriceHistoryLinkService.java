package br.com.project.service;

import br.com.project.model.ProductPriceHistoryLink;
import br.com.project.repository.ProductHistoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductPriceHistoryLinkService {

    private final ProductHistoryRepository repository;

    public ProductPriceHistoryLinkService(ProductHistoryRepository repository) {
        this.repository = repository;
    }

    public void save(ProductPriceHistoryLink link) {
        repository.save(link);
    }

    public List<ProductPriceHistoryLink> findAll() {
        return repository.findAll();
    }

    public void deleteByProductAndHistory(Integer productId, Integer historyId) {
        repository.deleteByProductAndHistory(productId, historyId);
    }
}
