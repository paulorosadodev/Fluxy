package br.com.project.service;

import br.com.project.dto.request.CategoryRequestDTO;
import br.com.project.dto.response.CategoryResponseDTO;
import br.com.project.model.Categoria;
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
        Categoria categoria = mapperUtils.map(categoriaRequestDTO, Categoria.class);
        categoriaRepository.save(categoria);
        return mapperUtils.map(categoria, CategoryResponseDTO.class);
    }

    public List<CategoryResponseDTO> listarTodos() {
        List<Categoria> categorias = categoriaRepository.findAll();
        return mapperUtils.mapList(categorias, CategoryResponseDTO.class);
    }

    public CategoryResponseDTO buscarPorCodigo(String codigo) {
        Optional<Categoria> categoria = categoriaRepository.findByCodigo(codigo);
        Categoria entity = categoria.orElseThrow(() -> new RuntimeException("Categoria não encontrada"));
        return mapperUtils.map(entity, CategoryResponseDTO.class);
    }

    public CategoryResponseDTO atualizar(String codigo, CategoryRequestDTO categoriaRequestDTO) {
        Categoria categoria = mapperUtils.map(categoriaRequestDTO, Categoria.class);
        categoria.setCodigo(codigo);
        categoriaRepository.update(categoria);
        return mapperUtils.map(categoria, CategoryResponseDTO.class);
    }

    public void deletar(String codigo) {
        categoriaRepository.deleteByCodigo(codigo);
    }
}
