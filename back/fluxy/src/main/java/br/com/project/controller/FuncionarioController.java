package br.com.project.controller;

import br.com.project.dto.request.EmployeeRequestDTO;
import br.com.project.dto.response.EmployeeResponseDTO;
import br.com.project.service.FuncionarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/funcionarios")
public class FuncionarioController {

    private final FuncionarioService funcionarioService;

    public FuncionarioController(FuncionarioService funcionarioService) {
        this.funcionarioService = funcionarioService;
    }

    @PostMapping
    public ResponseEntity<Void> salvar(@RequestBody EmployeeRequestDTO funcionarioRequestDTO) {
        funcionarioService.salvar(funcionarioRequestDTO);

        return ResponseEntity.ok().build();
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
    public ResponseEntity<Void> atualizar(@PathVariable Integer id, @RequestBody EmployeeRequestDTO funcionarioRequestDTO) {
        funcionarioService.atualizar(id, funcionarioRequestDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        funcionarioService.deletar(id);
        return ResponseEntity.ok().build();
    }
}
