package br.com.project.controller;

import br.com.project.model.Operacional;
import br.com.project.service.OperacionalService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/operacionais")
public class OperacionalController {

    private final OperacionalService operacionalService;

    public OperacionalController(OperacionalService operacionalService) {
        this.operacionalService = operacionalService;
    }

    @PostMapping
    public void salvar(@RequestBody Operacional operacional) {
        operacionalService.salvar(operacional);
    }

    @GetMapping("/{id}")
    public Optional<Operacional> buscarPorId(@PathVariable Integer id) {
        return operacionalService.buscarPorId(id);
    }

    @GetMapping
    public List<Operacional> listarTodos() {
        return operacionalService.listarTodos();
    }

    @PutMapping("/{id}")
    public void atualizar(@PathVariable Integer id, @RequestBody Operacional operacional) {
        operacional.setFkFuncionarioIdFuncionario(id);
        operacionalService.atualizar(operacional);
    }

    @DeleteMapping("/{id}")
    public void deletarPorId(@PathVariable Integer id) {
        operacionalService.deletarPorId(id);
    }
}
