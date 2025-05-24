package br.com.project.service;

import br.com.project.dto.request.ClientRequestDTO;
import br.com.project.dto.response.ClientCityCountDTO;
import br.com.project.dto.response.ClientResponseDTO;
import br.com.project.dto.response.ClientSpendingDTO;
import br.com.project.dto.response.TopTierClientDTO;
import br.com.project.model.Client;
import br.com.project.model.Phone;
import br.com.project.repository.ClientRepository;
import br.com.project.util.MapperUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClienteService {

    private final ClientRepository clienteRepository;
    private final TelefoneService telefoneService;
    private final MapperUtils mapperUtils;

    public ClienteService(ClientRepository clienteRepository, TelefoneService telefoneService, MapperUtils mapperUtils) {
        this.clienteRepository = clienteRepository;
        this.telefoneService = telefoneService;
        this.mapperUtils = mapperUtils;
    }

    @Transactional
    public void salvar(ClientRequestDTO clienteRequestDTO) {
        try {
            Client cliente = mapperUtils.map(clienteRequestDTO, Client.class);
            clienteRepository.save(cliente);

            List<Phone> telefones = clienteRequestDTO.getPhone().stream()
                    .map(numero -> new Phone(cliente.getIdClient(), numero))
                    .collect(Collectors.toList());

            telefones.forEach(telefoneService::salvar);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Transactional
    public void atualizar(Integer id, ClientRequestDTO clienteRequestDTO) {
        try {
            Client cliente = mapperUtils.map(clienteRequestDTO, Client.class);
            cliente.setIdClient(id);
            clienteRepository.update(cliente);

            telefoneService.deletarPorIdPessoa(id);

            List<Phone> telefones = clienteRequestDTO.getPhone().stream()
                    .map(numero -> new Phone(id, numero))
                    .collect(Collectors.toList());

            telefones.forEach(telefoneService::salvar);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public ClientResponseDTO buscarPorId(Integer id) {
        return clienteRepository.findById(id)
                .map(cliente -> mapperUtils.map(cliente, ClientResponseDTO.class))
                .orElseThrow(() -> new RuntimeException("Cliente com ID " + id + " não encontrado."));
    }

    public List<ClientResponseDTO> listarTodos() {
        return mapperUtils.mapList(clienteRepository.findAll(), ClientResponseDTO.class);
    }

    public void deletar(Integer id) {
        try {
            telefoneService.deletarPorIdPessoa(id);
            clienteRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public Integer buscarIdPorMatricula(String matricula) {
        try {
            return clienteRepository.findIdByPersonType(matricula);
        } catch (Exception e) {
            return null;
        }
    }

    public int getTotalClientsCount() {
        try {
            return clienteRepository.getTotalClientsCount();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public List<ClientCityCountDTO> getTotalClientByCity() {
        try {
            return clienteRepository.getTotalClientByCity();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public List<TopTierClientDTO> getTopTierClientsByPurchases() {
        try {
            return clienteRepository.getTopTierClientByPurchases();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public List<TopTierClientDTO> getLowTierClientsByPurchases() {
        try {
            return clienteRepository.getLowTierClientByPurchases();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public int getTotalPhysicalClientsCount() {
        try {
            return clienteRepository.getTotalPhysicalClientsCount();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public int getTotalJuridicalClientsCount() {
        try {
            return clienteRepository.getTotalJuridicalClientsCount();
        } catch (Exception e) {
             throw new RuntimeException(e.getMessage());
        }
    }

    public List<TopTierClientDTO> getActiveClients() {
        try {
            return clienteRepository.getActiveClients();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public List<ClientSpendingDTO> getTopTierSpendibleClient() {
        try {
            return clienteRepository.getTopTierSpendibleClient();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public List<ClientSpendingDTO> getLowTierSpendibleClient() {
        try {
            return clienteRepository.getLowTierSpendibleClient();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}