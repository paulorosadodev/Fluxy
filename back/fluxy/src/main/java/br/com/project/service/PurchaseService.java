package br.com.project.service;

import br.com.project.model.Purchase;
import br.com.project.repository.PurchaseRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PurchaseService {

    private final PurchaseRepository purchaseRepository;

    public PurchaseService(PurchaseRepository purchaseRepository) {
        this.purchaseRepository = purchaseRepository;
    }

    public Integer save(Purchase purchase) {
        return purchaseRepository.save(purchase);
    }

    public Optional<Purchase> findByNumber(Integer number) {
        return purchaseRepository.findByNumber(number);
    }

    public List<Purchase> findAll() {
        return purchaseRepository.findAll();
    }

    public void update(Purchase purchase) {
        purchaseRepository.update(purchase);
    }

    public void deleteByNumber(Integer number) {
        purchaseRepository.deleteByNumber(number);
    }
}
