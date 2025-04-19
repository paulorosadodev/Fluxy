package br.com.project.controller;

import br.com.project.model.Pessoa;
import br.com.project.service.PessoaService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/pessoas")
public class PessoaController {

    private final PessoaService pessoaService;

    public PessoaController(PessoaService pessoaService) {
        this.pessoaService = pessoaService;
    }

    @PostMapping
    public void salvar(@RequestBody Pessoa pessoa) {
        pessoaService.salvar(pessoa);
    }

    @GetMapping("/{id}")
    public Optional<Pessoa> buscarPorId(@PathVariable Integer id) {
        return pessoaService.buscarPorId(id);
    }

    @GetMapping
    public List<Pessoa> listarTodos() {
        return pessoaService.listarTodos();
    }

    @PutMapping("/{id}")
    public void atualizar(@PathVariable Integer id, @RequestBody Pessoa pessoa) {
        pessoa.setIdPessoa(id);
        pessoaService.atualizar(pessoa);
    }

    @DeleteMapping("/{id}")
    public void deletarPorId(@PathVariable Integer id) {
        pessoaService.deletarPorId(id);
    }
}
