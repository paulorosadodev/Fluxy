package br.com.project.controller;

import br.com.project.model.HistoricPriceProduct;
import br.com.project.service.HistoricoPrecoProdutoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/historicos-preco")
public class HistoricoPrecoProdutoController {

    private final HistoricoPrecoProdutoService historicoPrecoProdutoService;

    public HistoricoPrecoProdutoController(HistoricoPrecoProdutoService historicoPrecoProdutoService) {
        this.historicoPrecoProdutoService = historicoPrecoProdutoService;
    }

    @PostMapping
    public void salvar(@RequestBody HistoricPriceProduct historico) {
        historicoPrecoProdutoService.salvar(historico);
    }

    @GetMapping("/{id}")
    public Optional<HistoricPriceProduct> buscarPorId(@PathVariable Integer id) {
        return historicoPrecoProdutoService.buscarPorId(id);
    }

    @GetMapping
    public List<HistoricPriceProduct> listarTodos() {
        return historicoPrecoProdutoService.listarTodos();
    }

    @PutMapping("/{id}")
    public void atualizar(@PathVariable Integer id, @RequestBody HistoricPriceProduct historico) {
        historico.setIdHistoricoPrecoProduto(id);
        historicoPrecoProdutoService.atualizar(historico);
    }

    @DeleteMapping("/{id}")
    public void deletarPorId(@PathVariable Integer id) {
        historicoPrecoProdutoService.deletarPorId(id);
    }
}
