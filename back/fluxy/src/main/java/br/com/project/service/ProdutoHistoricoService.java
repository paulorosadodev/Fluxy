package br.com.project.service;

import br.com.project.model.ProdutoHistorico;
import br.com.project.repository.ProdutoHistoricoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoHistoricoService {

    private final ProdutoHistoricoRepository produtoHistoricoRepository;

    public ProdutoHistoricoService(ProdutoHistoricoRepository produtoHistoricoRepository) {
        this.produtoHistoricoRepository = produtoHistoricoRepository;
    }

    public void salvar(ProdutoHistorico produtoHistorico) {
        produtoHistoricoRepository.save(produtoHistorico);
    }

    public ProdutoHistorico buscar(Integer produtoId, Integer historicoPrecoProdutoId) {
        return produtoHistoricoRepository.findByIds(produtoId, historicoPrecoProdutoId)
                .orElseThrow(() -> new RuntimeException("Associação Produto-Histórico não encontrada"));
    }

    public List<ProdutoHistorico> listarTodos() {
        return produtoHistoricoRepository.findAll();
    }

    public void deletar(Integer produtoId, Integer historicoPrecoProdutoId) {
        produtoHistoricoRepository.delete(produtoId, historicoPrecoProdutoId);
    }
}
