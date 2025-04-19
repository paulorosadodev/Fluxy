package br.com.project.service;

import br.com.project.model.Cliente;
import br.com.project.repository.ClienteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public void salvar(Cliente cliente) {
        clienteRepository.save(cliente);
    }

    public Optional<Cliente> buscarPorId(Integer id) {
        return clienteRepository.findById(id);
    }

    public List<Cliente> listarTodos() {
        return clienteRepository.findAll();
    }

    public void atualizar(Cliente cliente) {
        clienteRepository.update(cliente);
    }

    public void deletarPorId(Integer id) {
        clienteRepository.deleteById(id);
    }
}
