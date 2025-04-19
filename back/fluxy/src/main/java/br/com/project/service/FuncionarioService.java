package br.com.project.service;

import br.com.project.model.Funcionario;
import br.com.project.repository.FuncionarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FuncionarioService {

    private final FuncionarioRepository funcionarioRepository;

    public FuncionarioService(FuncionarioRepository funcionarioRepository) {
        this.funcionarioRepository = funcionarioRepository;
    }

    public void salvar(Funcionario funcionario) {
        funcionarioRepository.save(funcionario);
    }

    public Optional<Funcionario> buscarPorId(Integer id) {
        return funcionarioRepository.findById(id);
    }

    public List<Funcionario> listarTodos() {
        return funcionarioRepository.findAll();
    }

    public void atualizar(Funcionario funcionario) {
        funcionarioRepository.update(funcionario);
    }

    public void deletarPorId(Integer id) {
        funcionarioRepository.deleteById(id);
    }
}
