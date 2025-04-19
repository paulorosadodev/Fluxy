package br.com.project.controller;

import br.com.project.model.ProdutoHistorico;
import br.com.project.service.ProdutoHistoricoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produto-historico")
public class ProdutoHistoricoController {

    private final ProdutoHistoricoService produtoHistoricoService;

    public ProdutoHistoricoController(ProdutoHistoricoService produtoHistoricoService) {
        this.produtoHistoricoService = produtoHistoricoService;
    }

    @PostMapping
    public void salvar(@RequestBody ProdutoHistorico produtoHistorico) {
        produtoHistoricoService.salvar(produtoHistorico);
    }

    @GetMapping
    public List<ProdutoHistorico> listarTodos() {
        return produtoHistoricoService.listarTodos();
    }

    @DeleteMapping
    public void deletar(@RequestParam Integer produtoId, @RequestParam Integer historicoId) {
        produtoHistoricoService.deletarPorIds(produtoId, historicoId);
    }
}
