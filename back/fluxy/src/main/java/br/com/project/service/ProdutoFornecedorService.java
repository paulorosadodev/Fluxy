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

    public List<ProdutoFornecedor> listarTodos() {
        return produtoFornecedorRepository.findAll();
    }

    public void deletarPorIds(Integer fornecedorId, Integer produtoId) {
        produtoFornecedorRepository.deleteByIds(fornecedorId, produtoId);
    }
}
