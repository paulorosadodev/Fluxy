package br.com.project.service;

import br.com.project.model.Pessoa;
import br.com.project.repository.PessoaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PessoaService {

    private final PessoaRepository pessoaRepository;

    public PessoaService(PessoaRepository pessoaRepository) {
        this.pessoaRepository = pessoaRepository;
    }

    public void salvar(Pessoa pessoa) {
        pessoaRepository.save(pessoa);
    }

    public Optional<Pessoa> buscarPorId(Integer id) {
        return pessoaRepository.findById(id);
    }

    public List<Pessoa> listarTodos() {
        return pessoaRepository.findAll();
    }

    public void atualizar(Pessoa pessoa) {
        pessoaRepository.update(pessoa);
    }

    public void deletarPorId(Integer id) {
        pessoaRepository.deleteById(id);
    }
}
