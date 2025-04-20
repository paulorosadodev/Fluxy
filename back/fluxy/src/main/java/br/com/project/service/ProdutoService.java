package br.com.project.service;

import br.com.project.dto.request.ProductRequestDTO;
import br.com.project.dto.response.ProductResponseDTO;
import br.com.project.model.Product;
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

    public ProductResponseDTO salvar(ProductRequestDTO produtoRequestDTO) {
        Product produto = mapperUtils.map(produtoRequestDTO, Product.class);

        // Primeiro salva o Produto e captura o ID
        Integer idProduto = produtoRepository.save(produto);

        // Agora salva o relacionamento produto-categoria
        produtoCategoriaService.salvar(idProduto, produtoRequestDTO.getCodigoCategoria());

        produto.setIdProduto(idProduto); // Atualiza o objeto para devolver no response

        return mapperUtils.map(produto, ProductResponseDTO.class);
    }

    public List<ProductResponseDTO> listarTodos() {
        return mapperUtils.mapList(produtoRepository.findAll(), ProductResponseDTO.class);
    }

    public ProductResponseDTO buscarPorId(Integer id) {
        Product produto = produtoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));
        return mapperUtils.map(produto, ProductResponseDTO.class);
    }

    public ProductResponseDTO atualizar(Integer id, ProductRequestDTO produtoRequestDTO) {
        Product produto = mapperUtils.map(produtoRequestDTO, Product.class);
        produto.setIdProduto(id);
        produtoRepository.update(produto);
        return mapperUtils.map(produto, ProductResponseDTO.class);
    }

    public void deletar(Integer id) {
        produtoRepository.deleteById(id);
    }
}
