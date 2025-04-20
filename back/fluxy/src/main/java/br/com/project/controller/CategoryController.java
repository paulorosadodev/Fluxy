package br.com.project.controller;

import br.com.project.model.Category;
import br.com.project.service.CategoryService;
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
    public void save(@RequestBody Category category) {
        service.save(category);
    }

    @GetMapping
    public List<Category> findAll() {
        return service.findAll();
    }

    @GetMapping("/{code}")
    public Optional<Category> findByCode(@PathVariable String code) {
        return service.findByCode(code);
    }

    @PutMapping("/{code}")
    public void update(@PathVariable String code, @RequestBody Category category) {
        category.setCode(code);
        service.update(category);
    }

    @DeleteMapping("/{code}")
    public void deleteByCode(@PathVariable String code) {
        service.deleteByCode(code);
    }
}
