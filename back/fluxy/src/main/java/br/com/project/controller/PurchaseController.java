package br.com.project.controller;

import br.com.project.model.Purchase;
import br.com.project.service.PurchaseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
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
    public ResponseEntity<Void> save(@RequestBody Purchase purchase) {
        Integer savedId = purchaseService.save(purchase);
        URI location = URI.create("/purchases/" + savedId);
        return ResponseEntity.created(location).build();
    }

    @GetMapping("/{number}")
    public ResponseEntity<Purchase> findByNumber(@PathVariable Integer number) {
        Optional<Purchase> purchase = purchaseService.findByNumber(number);
        return purchase.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Purchase>> findAll() {
        List<Purchase> purchases = purchaseService.findAll();
        return ResponseEntity.ok(purchases);
    }

    @PutMapping("/{number}")
    public ResponseEntity<Void> update(@PathVariable Integer number, @RequestBody Purchase purchase) {
        purchase.setNumber(number);
        purchaseService.update(purchase);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{number}")
    public ResponseEntity<Void> deleteByNumber(@PathVariable Integer number) {
        purchaseService.deleteByNumber(number);
        return ResponseEntity.noContent().build();
    }
}
