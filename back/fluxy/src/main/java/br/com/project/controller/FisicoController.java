package br.com.project.controller;

import br.com.project.model.Fisico;
import br.com.project.service.FisicoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/clientes/fisicos")
public class FisicoController {

    private final FisicoService fisicoService;

    public FisicoController(FisicoService fisicoService) {
        this.fisicoService = fisicoService;
    }

    @PostMapping
    public void salvar(@RequestBody Fisico fisico) {
        fisicoService.salvar(fisico);
    }

    @GetMapping("/{id}")
    public Optional<Fisico> buscarPorId(@PathVariable Integer id) {
        return fisicoService.buscarPorId(id);
    }

    @GetMapping
    public List<Fisico> listarTodos() {
        return fisicoService.listarTodos();
    }

    @PutMapping("/{id}")
    public void atualizar(@PathVariable Integer id, @RequestBody Fisico fisico) {
        fisico.setFkClienteId(id);
        fisicoService.atualizar(fisico);
    }

    @DeleteMapping("/{id}")
    public void deletarPorId(@PathVariable Integer id) {
        fisicoService.deletarPorId(id);
    }
}
