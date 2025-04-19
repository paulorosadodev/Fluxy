package br.com.project.service;

import br.com.project.model.Subgerente;
import br.com.project.repository.SubgerenteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SubgerenteService {

    private final SubgerenteRepository subgerenteRepository;

    public SubgerenteService(SubgerenteRepository subgerenteRepository) {
        this.subgerenteRepository = subgerenteRepository;
    }

    public void salvar(Subgerente subgerente) {
        subgerenteRepository.save(subgerente);
    }

    public Optional<Subgerente> buscarPorId(Integer id) {
        return subgerenteRepository.findById(id);
    }

    public List<Subgerente> listarTodos() {
        return subgerenteRepository.findAll();
    }

    public void atualizar(Subgerente subgerente) {
        subgerenteRepository.update(subgerente);
    }

    public void deletarPorId(Integer id) {
        subgerenteRepository.deleteById(id);
    }
}
