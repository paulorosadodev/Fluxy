package br.com.project.service;

import br.com.project.dto.request.ProdutoRequestDTO;
import br.com.project.dto.response.ProdutoResponseDTO;
import br.com.project.model.Produto;
import br.com.project.repository.ProdutoRepository;
import br.com.project.util.MapperUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository;
    private final ProdutoCategoriaService produtoCategoriaService;
    private final MapperUtils mapperUtils;

    public ProdutoService(ProdutoRepository produtoRepository, ProdutoCategoriaService produtoCategoriaService, MapperUtils mapperUtils) {
        this.produtoRepository = produtoRepository;
        this.produtoCategoriaService = produtoCategoriaService;
        this.mapperUtils = mapperUtils;
    }

    public ProdutoResponseDTO salvar(ProdutoRequestDTO produtoRequestDTO) {
        Produto produto = mapperUtils.map(produtoRequestDTO, Produto.class);

        // Primeiro salva o Produto e captura o ID
        Integer idProduto = produtoRepository.save(produto);

        // Agora salva o relacionamento produto-categoria
        produtoCategoriaService.salvar(idProduto, produtoRequestDTO.getCodigoCategoria());

        produto.setIdProduto(idProduto); // Atualiza o objeto para devolver no response

        return mapperUtils.map(produto, ProdutoResponseDTO.class);
    }

    public List<ProdutoResponseDTO> listarTodos() {
        return mapperUtils.mapList(produtoRepository.findAll(), ProdutoResponseDTO.class);
    }

    public ProdutoResponseDTO buscarPorId(Integer id) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));
        return mapperUtils.map(produto, ProdutoResponseDTO.class);
    }

    public ProdutoResponseDTO atualizar(Integer id, ProdutoRequestDTO produtoRequestDTO) {
        Produto produto = mapperUtils.map(produtoRequestDTO, Produto.class);
        produto.setIdProduto(id);
        produtoRepository.update(produto);
        return mapperUtils.map(produto, ProdutoResponseDTO.class);
    }

    public void deletar(Integer id) {
        produtoRepository.deleteById(id);
    }
}
