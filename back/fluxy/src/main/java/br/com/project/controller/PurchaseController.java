package br.com.project.controller;

import br.com.project.dto.request.PurchaseRequestDTO;
import br.com.project.dto.response.ProductResponseDTO;
import br.com.project.dto.response.PurchaseResponseDTO;
import br.com.project.model.Purchase;
import br.com.project.service.ProductService;
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
    public ResponseEntity<?> save(@RequestBody PurchaseRequestDTO requestDTO) {
        try {
            PurchaseResponseDTO responseDTO = purchaseService.save(requestDTO);
            return ResponseEntity.created(URI.create("/purchases/" + responseDTO.number())).body(responseDTO);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Erro ao salvar compra: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erro ao salvar compra: " + e.getMessage());
        }
    }

    @GetMapping("/{number}")
    public ResponseEntity<?> findByNumber(@PathVariable Integer number) {
        try {
            PurchaseResponseDTO response = purchaseService.findByNumber(number);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erro ao buscar número: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<PurchaseResponseDTO>> findAll() {
        try {
            List<PurchaseResponseDTO> purchases = purchaseService.findAll();
            return ResponseEntity.ok(purchases);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(null);
        }
    }

    @PutMapping("/{number}")
    public ResponseEntity<?> update(@PathVariable Integer number, @RequestBody PurchaseRequestDTO requestDTO) {
        try {
            purchaseService.update(number, requestDTO);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Erro ao atualizar compra: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erro ao atualizar compra: " + e.getMessage());
        }
    }

    @DeleteMapping("/{number}")
    public ResponseEntity<Void> deleteByNumber(@PathVariable Integer number) {
        try {
            purchaseService.deleteByNumber(number);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(null);
        }
    }
}
