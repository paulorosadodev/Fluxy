package br.com.project.controller;

import br.com.project.model.Subgerente;
import br.com.project.service.SubgerenteService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/subgerentes")
public class SubgerenteController {

    private final SubgerenteService subgerenteService;

    public SubgerenteController(SubgerenteService subgerenteService) {
        this.subgerenteService = subgerenteService;
    }

    @PostMapping
    public void salvar(@RequestBody Subgerente subgerente) {
        subgerenteService.salvar(subgerente);
    }

    @GetMapping("/{id}")
    public Optional<Subgerente> buscarPorId(@PathVariable Integer id) {
        return subgerenteService.buscarPorId(id);
    }

    @GetMapping
    public List<Subgerente> listarTodos() {
        return subgerenteService.listarTodos();
    }

    @PutMapping("/{id}")
    public void atualizar(@PathVariable Integer id, @RequestBody Subgerente subgerente) {
        subgerente.setFkFuncionarioIdFuncionario(id);
        subgerenteService.atualizar(subgerente);
    }

    @DeleteMapping("/{id}")
    public void deletarPorId(@PathVariable Integer id) {
        subgerenteService.deletarPorId(id);
    }
}
