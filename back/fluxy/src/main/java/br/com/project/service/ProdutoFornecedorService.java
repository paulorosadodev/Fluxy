package br.com.project.service;

import br.com.project.model.ProdutoFornecedor;
import br.com.project.repository.ProdutoFornecedorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoFornecedorService {

    private final ProdutoFornecedorRepository produtoFornecedorRepository;

    public ProdutoFornecedorService(ProdutoFornecedorRepository produtoFornecedorRepository) {
        this.produtoFornecedorRepository = produtoFornecedorRepository;
    }

    public void salvar(ProdutoFornecedor produtoFornecedor) {
        produtoFornecedorRepository.save(produtoFornecedor);
    }

    public ProdutoFornecedor buscar(Integer fornecedorId, Integer produtoId) {
        return produtoFornecedorRepository.findByIds(fornecedorId, produtoId)
                .orElseThrow(() -> new RuntimeException("Associação Produto-Fornecedor não encontrada"));
    }

    public List<ProdutoFornecedor> listarTodos() {
        return produtoFornecedorRepository.findAll();
    }

    public void deletar(Integer fornecedorId, Integer produtoId) {
        produtoFornecedorRepository.delete(fornecedorId, produtoId);
    }
}
