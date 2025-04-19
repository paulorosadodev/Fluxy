package br.com.project.controller;

import br.com.project.model.Juridico;
import br.com.project.service.JuridicoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/clientes/juridicos")
public class JuridicoController {

    private final JuridicoService juridicoService;

    public JuridicoController(JuridicoService juridicoService) {
        this.juridicoService = juridicoService;
    }

    @PostMapping
    public void salvar(@RequestBody Juridico juridico) {
        juridicoService.salvar(juridico);
    }

    @GetMapping("/{id}")
    public Optional<Juridico> buscarPorId(@PathVariable Integer id) {
        return juridicoService.buscarPorId(id);
    }

    @GetMapping
    public List<Juridico> listarTodos() {
        return juridicoService.listarTodos();
    }

    @PutMapping("/{id}")
    public void atualizar(@PathVariable Integer id, @RequestBody Juridico juridico) {
        juridico.setFkClienteId(id);
        juridicoService.atualizar(juridico);
    }

    @DeleteMapping("/{id}")
    public void deletarPorId(@PathVariable Integer id) {
        juridicoService.deletarPorId(id);
    }
}
