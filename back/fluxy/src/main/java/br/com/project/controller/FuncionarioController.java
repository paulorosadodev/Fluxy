package br.com.project.controller;

import br.com.project.model.Funcionario;
import br.com.project.service.FuncionarioService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/funcionarios")
public class FuncionarioController {

    private final FuncionarioService funcionarioService;

    public FuncionarioController(FuncionarioService funcionarioService) {
        this.funcionarioService = funcionarioService;
    }

    @PostMapping
    public void salvar(@RequestBody Funcionario funcionario) {
        funcionarioService.salvar(funcionario);
    }

    @GetMapping("/{id}")
    public Optional<Funcionario> buscarPorId(@PathVariable Integer id) {
        return funcionarioService.buscarPorId(id);
    }

    @GetMapping
    public List<Funcionario> listarTodos() {
        return funcionarioService.listarTodos();
    }

    @PutMapping("/{id}")
    public void atualizar(@PathVariable Integer id, @RequestBody Funcionario funcionario) {
        funcionario.setIdFuncionario(id);
        funcionarioService.atualizar(funcionario);
    }

    @DeleteMapping("/{id}")
    public void deletarPorId(@PathVariable Integer id) {
        funcionarioService.deletarPorId(id);
    }
}
