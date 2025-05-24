package br.com.project.controller;

import br.com.project.dto.request.EmployeeRequestDTO;
import br.com.project.dto.response.EmployeePerRoleCountResponseDTO;
import br.com.project.dto.response.EmployeePerShiftCountResponseDTO;
import br.com.project.dto.response.EmployeePurchaseCountResponseDTO;
import br.com.project.dto.response.EmployeeResponseDTO;
import br.com.project.model.Employer;
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
            return ResponseEntity.badRequest().body("Erro ao salvar funcionário: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erro interno ao salvar funcionário: " + e.getMessage());
        }
    }

    @GetMapping("/total")
    public ResponseEntity<?> countEmployees() {
        try {
            int total = funcionarioService.contarFuncionarios();
            return ResponseEntity.ok(total);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao contar funcionários: " + e.getMessage());
        }
    }

    @GetMapping("/most-purchases")
    public List<EmployeePurchaseCountResponseDTO> listEmployersByPurchasesDesc() {
        return funcionarioService.getEmployersByPurchaseCountDesc();
    }

    @GetMapping("/least-purchases")
    public List<EmployeePurchaseCountResponseDTO> listEmployersByPurchasesAsc() {
        return funcionarioService.getEmployersByPurchaseCountAsc();
    }

    @GetMapping("/total-salaries")
    public Double getTotalSalaries() {
        return funcionarioService.getTotalSalaries();
    }

    @GetMapping("/employee-per-shift")
    public List<EmployeePerShiftCountResponseDTO> getEmployeeCountByShift() {
        return funcionarioService.getEmployeeCountByShift();
    }

    @GetMapping("/employee-per-role")
    public List<EmployeePerRoleCountResponseDTO> getEmployeeCountByRole() {
        return funcionarioService.getEmployeeCountByRole();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Integer id) {
        EmployeeResponseDTO responseDTO = funcionarioService.buscarPorId(id);
        if (responseDTO == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping
    public ResponseEntity<?> listarTodos() {
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
