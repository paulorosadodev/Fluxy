package br.com.project.service;

import br.com.project.dto.request.ClientRequestDTO;
import br.com.project.dto.response.ClientResponseDTO;
import br.com.project.model.Cliente;
import br.com.project.model.Telefone;
import br.com.project.repository.ClienteRepository;
import br.com.project.util.MapperUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final TelefoneService telefoneService;
    private final MapperUtils mapperUtils;

    public ClienteService(ClienteRepository clienteRepository, TelefoneService telefoneService, MapperUtils mapperUtils) {
        this.clienteRepository = clienteRepository;
        this.telefoneService = telefoneService;
        this.mapperUtils = mapperUtils;
    }

    @Transactional
    public void salvar(ClientRequestDTO clienteRequestDTO) {
        Cliente cliente = mapperUtils.map(clienteRequestDTO, Cliente.class);
        clienteRepository.save(cliente);

        List<Telefone> telefones = clienteRequestDTO.getTelefones().stream()
                .map(numero -> new Telefone(numero, cliente.getIdCliente()))
                .collect(Collectors.toList());

        telefones.forEach(telefoneService::salvar);
    }

    @Transactional
    public void atualizar(Integer id, ClientRequestDTO clienteRequestDTO) {
        Cliente cliente = mapperUtils.map(clienteRequestDTO, Cliente.class);
        cliente.setIdCliente(id);
        clienteRepository.update(cliente);

        telefoneService.deletarPorIdPessoa(id);

        List<Telefone> telefones = clienteRequestDTO.getTelefones().stream()
                .map(numero -> new Telefone(numero, id))
                .collect(Collectors.toList());

        telefones.forEach(telefoneService::salvar);
    }

    public ClientResponseDTO buscarPorId(Integer id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
        return mapperUtils.map(cliente, ClientResponseDTO.class);
    }

    public List<ClientResponseDTO> listarTodos() {
        List<Cliente> clientes = clienteRepository.findAll();
        return mapperUtils.mapList(clientes, ClientResponseDTO.class);
    }

    public void deletar(Integer id) {
        telefoneService.deletarPorIdPessoa(id);
        clienteRepository.deleteById(id);
    }
}
