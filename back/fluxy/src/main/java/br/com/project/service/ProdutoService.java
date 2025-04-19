package br.com.project.service;

import br.com.project.dto.request.ProdutoRequestDTO;
import br.com.project.dto.response.ProdutoResponseDTO;
import br.com.project.model.Produto;
import br.com.project.repository.ProdutoRepository;
import br.com.project.util.MapperUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository;
    private final MapperUtils mapperUtils;

    public ProdutoService(ProdutoRepository produtoRepository, MapperUtils mapperUtils) {
        this.produtoRepository = produtoRepository;
        this.mapperUtils = mapperUtils;
    }

    public void salvar(ProdutoRequestDTO produtoRequestDTO) {
        Produto produto = mapperUtils.map(produtoRequestDTO, Produto.class);
        System.out.println(produto);
        produtoRepository.save(produto);
    }

    public Optional<ProdutoResponseDTO> buscarPorId(Integer id) {
        return produtoRepository.findById(id)
                .map(produto -> mapperUtils.map(produto, ProdutoResponseDTO.class));
    }

    public List<ProdutoResponseDTO> listarTodos() {
        List<Produto> produtos = produtoRepository.findAll();
        return mapperUtils.mapList(produtos, ProdutoResponseDTO.class);
    }

    public void atualizar(Integer id, ProdutoRequestDTO produtoRequestDTO) {
        Produto produto = mapperUtils.map(produtoRequestDTO, Produto.class);
        produto.setIdProduto(id);
        produtoRepository.update(produto);
    }

    public void deletarPorId(Integer id) {
        produtoRepository.deleteById(id);
    }
}
