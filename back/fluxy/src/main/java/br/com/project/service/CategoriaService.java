package br.com.project.service;

import br.com.project.dto.request.CategoriaRequestDTO;
import br.com.project.dto.response.CategoriaResponseDTO;
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

    public CategoriaResponseDTO salvar(CategoriaRequestDTO categoriaRequestDTO) {
        Categoria categoria = mapperUtils.map(categoriaRequestDTO, Categoria.class);
        categoriaRepository.save(categoria);
        return mapperUtils.map(categoria, CategoriaResponseDTO.class);
    }

    public List<CategoriaResponseDTO> listarTodos() {
        List<Categoria> categorias = categoriaRepository.findAll();
        return mapperUtils.mapList(categorias, CategoriaResponseDTO.class);
    }

    public CategoriaResponseDTO buscarPorCodigo(String codigo) {
        Optional<Categoria> categoria = categoriaRepository.findByCodigo(codigo);
        Categoria entity = categoria.orElseThrow(() -> new RuntimeException("Categoria não encontrada"));
        return mapperUtils.map(entity, CategoriaResponseDTO.class);
    }

    public CategoriaResponseDTO atualizar(String codigo, CategoriaRequestDTO categoriaRequestDTO) {
        Categoria categoria = mapperUtils.map(categoriaRequestDTO, Categoria.class);
        categoria.setCodigo(codigo);
        categoriaRepository.update(categoria);
        return mapperUtils.map(categoria, CategoriaResponseDTO.class);
    }

    public void deletar(String codigo) {
        categoriaRepository.deleteByCodigo(codigo);
    }
}
