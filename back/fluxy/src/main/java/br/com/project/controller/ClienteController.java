package br.com.project.controller;

import br.com.project.dto.request.ClientRequestDTO;
import br.com.project.dto.response.ClientResponseDTO;
import br.com.project.service.ClienteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @PostMapping
    public ResponseEntity<Void> salvar(@RequestBody ClientRequestDTO clienteRequestDTO) {
        clienteService.salvar(clienteRequestDTO);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientResponseDTO> buscarPorId(@PathVariable Integer id) {
        ClientResponseDTO responseDTO = clienteService.buscarPorId(id);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping
    public ResponseEntity<List<ClientResponseDTO>> listarTodos() {
        List<ClientResponseDTO> clientes = clienteService.listarTodos();
        return ResponseEntity.ok(clientes);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> atualizar(@PathVariable Integer id, @RequestBody ClientRequestDTO clienteRequestDTO) {
        clienteService.atualizar(id, clienteRequestDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        clienteService.deletar(id);
        return ResponseEntity.ok().build();
    }
}
