package br.com.project.service;

import br.com.project.model.Categoria;
import br.com.project.repository.CategoriaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    public CategoriaService(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    public void salvar(Categoria categoria) {
        categoriaRepository.save(categoria);
    }

    public Optional<Categoria> buscarPorCodigo(String codigo) {
        return categoriaRepository.findByCodigo(codigo);
    }

    public List<Categoria> listarTodos() {
        return categoriaRepository.findAll();
    }

    public void atualizar(Categoria categoria) {
        categoriaRepository.update(categoria);
    }

    public void deletarPorCodigo(String codigo) {
        categoriaRepository.deleteByCodigo(codigo);
    }
}
