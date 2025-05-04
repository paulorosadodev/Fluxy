package br.com.project.controller;

import br.com.project.dto.request.ProductRequestDTO;
import br.com.project.dto.response.ProductResponseDTO;
import br.com.project.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody ProductRequestDTO requestDTO) {
        try {
            ProductResponseDTO response = productService.save(requestDTO);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Erro ao salvar produto: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erro ao salvar produto: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> findAll() {
        try {
            List<ProductResponseDTO> products = productService.findAll();
            return ResponseEntity.ok(products);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erro ao listar produtos: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Integer id) {
        try {
            ProductResponseDTO response = productService.findById(id);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erro ao buscar produto: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody ProductRequestDTO requestDTO) {
        try {
            ProductResponseDTO response = productService.update(id, requestDTO);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Erro ao atualizar produto: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erro ao atualizar produto: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        try {
            productService.delete(id);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erro ao deletar produto: " + e.getMessage());
        }
    }
}
