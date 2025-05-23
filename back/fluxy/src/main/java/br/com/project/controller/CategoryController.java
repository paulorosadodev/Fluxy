package br.com.project.controller;

import br.com.project.dto.response.CategoryPurchasedResponseDTO;
import br.com.project.model.Category;
import br.com.project.service.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService service;

    public CategoryController(CategoryService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody Category category) {
        try {
            service.save(category);
            return ResponseEntity.status(CREATED).build();
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(INTERNAL_SERVER_ERROR, "Erro ao salvar categoria.");
        }
    }

    @GetMapping
    public ResponseEntity<List<Category>> findAll() {
        try {
            return ResponseEntity.ok(service.findAll());
        } catch (Exception e) {
            throw new ResponseStatusException(INTERNAL_SERVER_ERROR, "Erro ao buscar categorias.");
        }
    }

    @GetMapping("/total-categories")
    public ResponseEntity<?> getTotalCategoriesCount() {
        try {
            int totalCategories = service.getTotalCategoriesCount();
            return ResponseEntity.ok(totalCategories);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erro ao obter total de categorias: " + e.getMessage());
        }
    }

    @GetMapping("/most-purchased-categories")
    public ResponseEntity<List<CategoryPurchasedResponseDTO>> getCategoryPurchaseSummary() {
        return ResponseEntity.ok(service.getCategoryPurchaseSummary());
    }


}
