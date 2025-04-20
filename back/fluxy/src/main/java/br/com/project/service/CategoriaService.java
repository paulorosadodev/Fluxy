package br.com.project.service;

import br.com.project.dto.request.CategoryRequestDTO;
import br.com.project.dto.response.CategoryResponseDTO;
import br.com.project.model.Category;
import br.com.project.repository.CategoriaRepository;
import br.com.project.util.MapperUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;
    private final MapperUtils mapperUtils;

    public CategoriaService(CategoriaRepository categoriaRepository, MapperUtils mapperUtils) {
        this.categoriaRepository = categoriaRepository;
        this.mapperUtils = mapperUtils;
    }

    public CategoryResponseDTO salvar(CategoryRequestDTO categoriaRequestDTO) {
        Category categoria = mapperUtils.map(categoriaRequestDTO, Category.class);
        categoriaRepository.save(categoria);
        return mapperUtils.map(categoria, CategoryResponseDTO.class);
    }

    public List<CategoryResponseDTO> listarTodos() {
        List<Category> categorias = categoriaRepository.findAll();
        return mapperUtils.mapList(categorias, CategoryResponseDTO.class);
    }

    public CategoryResponseDTO buscarPorCodigo(String codigo) {
        Optional<Category> categoria = categoriaRepository.findByCodigo(codigo);
        Category entity = categoria.orElseThrow(() -> new RuntimeException("Categoria não encontrada"));
        return mapperUtils.map(entity, CategoryResponseDTO.class);
    }

    public CategoryResponseDTO atualizar(String codigo, CategoryRequestDTO categoriaRequestDTO) {
        Category categoria = mapperUtils.map(categoriaRequestDTO, Category.class);
        categoria.setCodigo(codigo);
        categoriaRepository.update(categoria);
        return mapperUtils.map(categoria, CategoryResponseDTO.class);
    }

    public void deletar(String codigo) {
        categoriaRepository.deleteByCodigo(codigo);
    }
}
