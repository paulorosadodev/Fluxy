package br.com.project.controller;

import br.com.project.model.Gerente;
import br.com.project.service.GerenteService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/gerentes")
public class GerenteController {

    private final GerenteService gerenteService;

    public GerenteController(GerenteService gerenteService) {
        this.gerenteService = gerenteService;
    }

    @PostMapping
    public void salvar(@RequestBody Gerente gerente) {
        gerenteService.salvar(gerente);
    }

    @GetMapping("/{id}")
    public Optional<Gerente> buscarPorId(@PathVariable Integer id) {
        return gerenteService.buscarPorId(id);
    }

    @GetMapping
    public List<Gerente> listarTodos() {
        return gerenteService.listarTodos();
    }

    @PutMapping("/{id}")
    public void atualizar(@PathVariable Integer id, @RequestBody Gerente gerente) {
        gerente.setFkFuncionarioMatricula(id);
        gerenteService.atualizar(gerente);
    }

    @DeleteMapping("/{id}")
    public void deletarPorId(@PathVariable Integer id) {
        gerenteService.deletarPorId(id);
    }
}
