package br.com.project.service;

import br.com.project.model.ProductHistoric;
import br.com.project.repository.ProdutoHistoricoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoHistoricoService {

    private final ProdutoHistoricoRepository produtoHistoricoRepository;

    public ProdutoHistoricoService(ProdutoHistoricoRepository produtoHistoricoRepository) {
        this.produtoHistoricoRepository = produtoHistoricoRepository;
    }

    public void salvar(ProductHistoric produtoHistorico) {
        produtoHistoricoRepository.save(produtoHistorico);
    }

    public ProductHistoric buscar(Integer produtoId, Integer historicoPrecoProdutoId) {
        return produtoHistoricoRepository.findByIds(produtoId, historicoPrecoProdutoId)
                .orElseThrow(() -> new RuntimeException("Associação Produto-Histórico não encontrada"));
    }

    public List<ProductHistoric> listarTodos() {
        return produtoHistoricoRepository.findAll();
    }

    public void deletar(Integer produtoId, Integer historicoPrecoProdutoId) {
        produtoHistoricoRepository.delete(produtoId, historicoPrecoProdutoId);
    }
}
