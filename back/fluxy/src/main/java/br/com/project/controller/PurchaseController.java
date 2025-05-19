package br.com.project.controller;

import br.com.project.dto.request.PurchaseRequestDTO;
import br.com.project.dto.response.PurchaseResponseDTO;
import br.com.project.service.PurchaseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/purchases")
public class PurchaseController {

    private final PurchaseService service;

    public PurchaseController(PurchaseService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody PurchaseRequestDTO dto) {
        try {
            PurchaseResponseDTO responseDTO = service.save(dto);
            URI location = URI.create("/purchases/" + responseDTO.getNumber());
            return ResponseEntity.created(location).body(responseDTO);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Erro ao salvar compra: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro interno ao salvar compra: " + e.getMessage());
        }
    }

    @GetMapping("/{number}")
    public ResponseEntity<?> findByNumber(@PathVariable Integer number) {
        try {
            PurchaseResponseDTO responseDTO = service.findByNumber(number);
            return ResponseEntity.ok(responseDTO);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao buscar compra: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> findAll() {
        try {
            List<PurchaseResponseDTO> responseList = service.findAll();
            return ResponseEntity.ok(responseList);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao listar compras: " + e.getMessage());
        }
    }

    @PutMapping("/{number}")
    public ResponseEntity<?> update(@PathVariable Integer number, @RequestBody PurchaseRequestDTO dto) {
        try {
            service.update(number, dto);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Erro ao atualizar compra: " + e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro interno ao atualizar compra: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro inesperado ao atualizar compra: " + e.getMessage());
        }
    }

    @DeleteMapping("/{number}")
    public ResponseEntity<?> deleteByNumber(@PathVariable Integer number) {
        try {
            service.deleteByNumber(number);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao deletar compra: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro inesperado ao deletar compra: " + e.getMessage());
        }
    }
}
