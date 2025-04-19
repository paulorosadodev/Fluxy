package br.com.project.controller;

import br.com.project.model.Compra;
import br.com.project.service.CompraService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/compras")
public class CompraController {

    private final CompraService compraService;

    public CompraController(CompraService compraService) {
        this.compraService = compraService;
    }

    @PostMapping
    public void salvar(@RequestBody Compra compra) {
        compraService.salvar(compra);
    }

    @GetMapping("/{numero}")
    public Optional<Compra> buscarPorNumero(@PathVariable Integer numero) {
        return compraService.buscarPorNumero(numero);
    }

    @GetMapping
    public List<Compra> listarTodos() {
        return compraService.listarTodos();
    }

    @PutMapping("/{numero}")
    public void atualizar(@PathVariable Integer numero, @RequestBody Compra compra) {
        compra.setNumero(numero);
        compraService.atualizar(compra);
    }

    @DeleteMapping("/{numero}")
    public void deletarPorNumero(@PathVariable Integer numero) {
        compraService.deletarPorNumero(numero);
    }
}
