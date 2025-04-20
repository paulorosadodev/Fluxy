package br.com.project.controller;

import br.com.project.model.Purchase;
import br.com.project.service.PurchaseService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/purchases")
public class PurchaseController {

    private final PurchaseService purchaseService;

    public PurchaseController(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    @PostMapping
    public Integer save(@RequestBody Purchase purchase) {
        return purchaseService.save(purchase);
    }

    @GetMapping("/{number}")
    public Optional<Purchase> findByNumber(@PathVariable Integer number) {
        return purchaseService.findByNumber(number);
    }

    @GetMapping
    public List<Purchase> findAll() {
        return purchaseService.findAll();
    }

    @PutMapping("/{number}")
    public void update(@PathVariable Integer number, @RequestBody Purchase purchase) {
        purchase.setNumber(number);
        purchaseService.update(purchase);
    }

    @DeleteMapping("/{number}")
    public void deleteByNumber(@PathVariable Integer number) {
        purchaseService.deleteByNumber(number);
    }
}
