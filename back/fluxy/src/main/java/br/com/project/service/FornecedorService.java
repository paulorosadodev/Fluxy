package br.com.project.service;

import br.com.project.model.Fornecedor;
import br.com.project.repository.FornecedorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FornecedorService {

    private final FornecedorRepository fornecedorRepository;

    public FornecedorService(FornecedorRepository fornecedorRepository) {
        this.fornecedorRepository = fornecedorRepository;
    }

    public void salvar(Fornecedor fornecedor) {
        fornecedorRepository.save(fornecedor);
    }

    public Optional<Fornecedor> buscarPorId(Integer id) {
        return fornecedorRepository.findById(id);
    }

    public List<Fornecedor> listarTodos() {
        return fornecedorRepository.findAll();
    }

    public void atualizar(Fornecedor fornecedor) {
        fornecedorRepository.update(fornecedor);
    }

    public void deletarPorId(Integer id) {
        fornecedorRepository.deleteById(id);
    }
}
