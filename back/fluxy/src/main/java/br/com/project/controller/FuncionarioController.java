package br.com.project.controller;

import br.com.project.dto.request.EmployeeRequestDTO;
import br.com.project.dto.response.EmployeeResponseDTO;
import br.com.project.service.FuncionarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class FuncionarioController {

    private final FuncionarioService funcionarioService;

    public FuncionarioController(FuncionarioService funcionarioService) {
        this.funcionarioService = funcionarioService;
    }

    @PostMapping
    public ResponseEntity<?> salvar(@RequestBody EmployeeRequestDTO funcionarioRequestDTO) {
        try {
            funcionarioService.salvar(funcionarioRequestDTO);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeResponseDTO> buscarPorId(@PathVariable Integer id) {
        EmployeeResponseDTO responseDTO = funcionarioService.buscarPorId(id);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping
    public ResponseEntity<List<EmployeeResponseDTO>> listarTodos() {
        List<EmployeeResponseDTO> funcionarios = funcionarioService.listarTodos();
        return ResponseEntity.ok(funcionarios);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Integer id, @RequestBody EmployeeRequestDTO funcionarioRequestDTO) {
        try {
            funcionarioService.atualizar(id, funcionarioRequestDTO);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (RuntimeException e) {
            if (e.getMessage().contains("Funcionário não encontrado") || e.getMessage().contains("Pessoa não encontrada")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Integer id) {
        try {
            funcionarioService.deletar(id);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            if (e.getMessage().contains("Funcionário não encontrado")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao deletar funcionário: " + e.getMessage());
        }
    }

}
