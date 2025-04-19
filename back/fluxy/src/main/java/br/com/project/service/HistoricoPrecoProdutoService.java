package br.com.project.service;

import br.com.project.model.HistoricoPrecoProduto;
import br.com.project.repository.HistoricoPrecoProdutoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HistoricoPrecoProdutoService {

    private final HistoricoPrecoProdutoRepository historicoPrecoProdutoRepository;

    public HistoricoPrecoProdutoService(HistoricoPrecoProdutoRepository historicoPrecoProdutoRepository) {
        this.historicoPrecoProdutoRepository = historicoPrecoProdutoRepository;
    }

    public void salvar(HistoricoPrecoProduto historico) {
        historicoPrecoProdutoRepository.save(historico);
    }

    public Optional<HistoricoPrecoProduto> buscarPorId(Integer id) {
        return historicoPrecoProdutoRepository.findById(id);
    }

    public List<HistoricoPrecoProduto> listarTodos() {
        return historicoPrecoProdutoRepository.findAll();
    }

    public void atualizar(HistoricoPrecoProduto historico) {
        historicoPrecoProdutoRepository.update(historico);
    }

    public void deletarPorId(Integer id) {
        historicoPrecoProdutoRepository.deleteById(id);
    }
}
