package br.com.project.service;

import br.com.project.model.Fisico;
import br.com.project.repository.FisicoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FisicoService {

    private final FisicoRepository fisicoRepository;

    public FisicoService(FisicoRepository fisicoRepository) {
        this.fisicoRepository = fisicoRepository;
    }

    public void salvar(Fisico fisico) {
        fisicoRepository.save(fisico);
    }

    public Optional<Fisico> buscarPorId(Integer id) {
        return fisicoRepository.findById(id);
    }

    public List<Fisico> listarTodos() {
        return fisicoRepository.findAll();
    }

    public void atualizar(Fisico fisico) {
        fisicoRepository.update(fisico);
    }

    public void deletarPorId(Integer id) {
        fisicoRepository.deleteById(id);
    }
}
