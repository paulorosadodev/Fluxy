package br.com.project.controller;

import br.com.project.model.Category;
import br.com.project.service.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService service;

    public CategoryController(CategoryService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody Category category) {
        service.save(category);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<Category>> findAll() {
        List<Category> categories = service.findAll();
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/{code}")
    public ResponseEntity<Category> findByCode(@PathVariable String code) {
        Optional<Category> category = service.findByCode(code);
        return category.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{code}")
    public ResponseEntity<Void> update(@PathVariable String code, @RequestBody Category category) {
        category.setCode(code);
        service.update(category);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{code}")
    public ResponseEntity<Void> deleteByCode(@PathVariable String code) {
        service.deleteByCode(code);
        return ResponseEntity.ok().build();
    }
}
