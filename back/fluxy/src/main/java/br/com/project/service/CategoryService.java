package br.com.project.service;

import br.com.project.model.Category;
import br.com.project.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    private final CategoryRepository repository;

    public CategoryService(CategoryRepository repository) {
        this.repository = repository;
    }

    public void save(Category category) {
        if (category.getCode() == null || category.getName() == null) {
            throw new IllegalArgumentException("Código e nome da categoria são obrigatórios.");
        }
        repository.save(category);
    }

    public List<Category> findAll() {
        return repository.findAll();
    }

    public Optional<Category> findByCode(String code) {
        return repository.findByCode(code);
    }

    public void update(Category category) {
        Optional<Category> existing = repository.findByCode(category.getCode());
        if (existing.isEmpty()) {
            throw new IllegalArgumentException("Categoria não encontrada para atualização.");
        }
        repository.update(category);
    }

    public void deleteByCode(String code) {
        Optional<Category> existing = repository.findByCode(code);
        if (existing.isEmpty()) {
            throw new IllegalArgumentException("Categoria não encontrada para exclusão.");
        }
        repository.deleteByCode(code);
    }
}
