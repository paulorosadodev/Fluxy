package br.com.project.controller;

import br.com.project.dto.request.ClientRequestDTO;
import br.com.project.dto.response.ClientCityCountDTO;
import br.com.project.dto.response.ClientResponseDTO;
import br.com.project.dto.response.TopTierClientDTO;
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
    public ResponseEntity<?> salvar(@RequestBody ClientRequestDTO clienteRequestDTO) {
        try {
            clienteService.salvar(clienteRequestDTO);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao salvar cliente: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Integer id) {
        try {
            ClientResponseDTO responseDTO = clienteService.buscarPorId(id);
            return ResponseEntity.ok(responseDTO);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erro ao buscar cliente: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> listarTodos() {
        try {
            List<ClientResponseDTO> clientes = clienteService.listarTodos();
            return ResponseEntity.ok(clientes);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erro ao listar clientes: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Integer id, @RequestBody ClientRequestDTO clienteRequestDTO) {
        try {
            clienteService.atualizar(id, clienteRequestDTO);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao atualizar cliente: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Integer id) {
        try {
            clienteService.deletar(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erro ao deletar cliente: " + e.getMessage());
        }
    }

    @GetMapping("/total-clients")
    public ResponseEntity<?> getTotalClientsCount() {
        try {
            int totalClients = clienteService.getTotalClientsCount();
            return ResponseEntity.ok(totalClients);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erro ao buscar total de clientes: " + e.getMessage());
        }
    }

    @GetMapping("/total-clients-by-city")
    public ResponseEntity<?> getTotalClientByCity() {
        try {
            List<ClientCityCountDTO> totalClientOnCities = clienteService.getTotalClientByCity();
            return ResponseEntity.ok(totalClientOnCities);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erro ao buscar total de clientes por cidade: " + e.getMessage());
        }
    }

    @GetMapping("/top-tier-clients")
    public ResponseEntity<?> getTopTierClientsByPurchases() {
        try {
            List<TopTierClientDTO> topTierClients = clienteService.getTopTierClientsByPurchases();
            return ResponseEntity.ok(topTierClients);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erro ao buscar clientes de nível superior por compras: " + e.getMessage());
        }
    }

    @GetMapping("/low-tier-clients")
    public ResponseEntity<?> getLowTierClientsByPurchases() {
        try {
            List<TopTierClientDTO> lowTierClients = clienteService.getLowTierClientsByPurchases();
            return ResponseEntity.ok(lowTierClients);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erro ao buscar clientes de nível inferior por compras: " + e.getMessage());
        }
    }

    @GetMapping("/total-physical-clients")
    public ResponseEntity<?> getTotalPhysicalClientsCount() {
        try {
            int totalPhysicalClients = clienteService.getTotalPhysicalClientsCount();
            return ResponseEntity.ok(totalPhysicalClients);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erro ao buscar total de clientes físicos: " + e.getMessage());
        }
    }

    @GetMapping("/total-juridical-clients")
    public ResponseEntity<?> getTotalJuridicalClientsCount() {
        try {
            int totalJuridicalClients = clienteService.getTotalJuridicalClientsCount();
            return ResponseEntity.ok(totalJuridicalClients);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erro ao buscar total de clientes jurídicos: " + e.getMessage());
        }
    }

    @GetMapping("/active-clients")
    public ResponseEntity<?> getActiveClients() {
        try {
            List<TopTierClientDTO> mostActiveClients = clienteService.getActiveClients();
            return ResponseEntity.ok(mostActiveClients);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erro ao buscar clientes ativos: " + e.getMessage());
        }
    }
}