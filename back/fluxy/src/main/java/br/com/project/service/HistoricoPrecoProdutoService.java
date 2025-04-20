package br.com.project.service;

import br.com.project.model.HistoricPriceProduct;
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

    public void salvar(HistoricPriceProduct historico) {
        historicoPrecoProdutoRepository.save(historico);
    }

    public Optional<HistoricPriceProduct> buscarPorId(Integer id) {
        return historicoPrecoProdutoRepository.findById(id);
    }

    public List<HistoricPriceProduct> listarTodos() {
        return historicoPrecoProdutoRepository.findAll();
    }

    public void atualizar(HistoricPriceProduct historico) {
        historicoPrecoProdutoRepository.update(historico);
    }

    public void deletarPorId(Integer id) {
        historicoPrecoProdutoRepository.deleteById(id);
    }
}
