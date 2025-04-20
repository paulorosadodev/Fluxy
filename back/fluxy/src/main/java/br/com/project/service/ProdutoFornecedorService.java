package br.com.project.service;

import br.com.project.model.ProductSupplier;
import br.com.project.repository.ProdutoFornecedorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoFornecedorService {

    private final ProdutoFornecedorRepository produtoFornecedorRepository;

    public ProdutoFornecedorService(ProdutoFornecedorRepository produtoFornecedorRepository) {
        this.produtoFornecedorRepository = produtoFornecedorRepository;
    }

    public void salvar(ProductSupplier produtoFornecedor) {
        produtoFornecedorRepository.save(produtoFornecedor);
    }

    public ProductSupplier buscar(Integer fornecedorId, Integer produtoId) {
        return produtoFornecedorRepository.findByIds(fornecedorId, produtoId)
                .orElseThrow(() -> new RuntimeException("Associação Produto-Fornecedor não encontrada"));
    }

    public List<ProductSupplier> listarTodos() {
        return produtoFornecedorRepository.findAll();
    }

    public void deletar(Integer fornecedorId, Integer produtoId) {
        produtoFornecedorRepository.delete(fornecedorId, produtoId);
    }
}
