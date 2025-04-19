package br.com.project.controller;

import br.com.project.model.Categoria;
import br.com.project.service.CategoriaService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {

    private final CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @PostMapping
    public void salvar(@RequestBody Categoria categoria) {
        categoriaService.salvar(categoria);
    }

    @GetMapping("/{codigo}")
    public Optional<Categoria> buscarPorCodigo(@PathVariable String codigo) {
        return categoriaService.buscarPorCodigo(codigo);
    }

    @GetMapping
    public List<Categoria> listarTodos() {
        return categoriaService.listarTodos();
    }

    @PutMapping("/{codigo}")
    public void atualizar(@PathVariable String codigo, @RequestBody Categoria categoria) {
        categoria.setCodigo(codigo);
        categoriaService.atualizar(categoria);
    }

    @DeleteMapping("/{codigo}")
    public void deletarPorCodigo(@PathVariable String codigo) {
        categoriaService.deletarPorCodigo(codigo);
    }
}
