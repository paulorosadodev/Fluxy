package br.com.project.controller;

import br.com.project.dto.request.ProductSupplierRequestDTO;
import br.com.project.dto.response.ProductSupplierResponseDTO;
import br.com.project.service.ProductSupplierService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/supply")
public class ProductSupplierController {

    private final ProductSupplierService service;

    public ProductSupplierController(ProductSupplierService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody ProductSupplierRequestDTO dto) {
        try {
            service.salvar(dto);
            return ResponseEntity.status(CREATED).build();
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(INTERNAL_SERVER_ERROR, "Erro ao salvar entrega." + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<ProductSupplierResponseDTO>> findAll() {
        try {
            List<ProductSupplierResponseDTO> productSuppliers = service.findAll();
            System.out.println(productSuppliers);
            return ResponseEntity.ok(productSuppliers);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(INTERNAL_SERVER_ERROR, "Erro ao listar entrega." + e.getMessage() );
        }
    }

    @PutMapping("/{fornecedorId}/{produtoId}")
    public ResponseEntity<Void> update(@PathVariable Integer fornecedorId,
                                       @PathVariable Integer produtoId,
                                       @RequestBody ProductSupplierRequestDTO dto) {
        try {
            service.update(fornecedorId, produtoId, dto);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(INTERNAL_SERVER_ERROR, "Erro ao atualizar entrega.");
        }
    }

    @DeleteMapping("/{fornecedorId}/{produtoId}")
    public ResponseEntity<Void> delete(@PathVariable Integer fornecedorId, @PathVariable Integer produtoId) {
        try {
            service.deleteBySupplierAndProduct(fornecedorId, produtoId);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(NOT_FOUND, e.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(INTERNAL_SERVER_ERROR, "Erro ao deletar entrega.");
        }
    }
}
