package br.com.project.service;

import br.com.project.model.Juridico;
import br.com.project.repository.JuridicoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JuridicoService {

    private final JuridicoRepository juridicoRepository;

    public JuridicoService(JuridicoRepository juridicoRepository) {
        this.juridicoRepository = juridicoRepository;
    }

    public void salvar(Juridico juridico) {
        juridicoRepository.save(juridico);
    }

    public Optional<Juridico> buscarPorId(Integer id) {
        return juridicoRepository.findById(id);
    }

    public List<Juridico> listarTodos() {
        return juridicoRepository.findAll();
    }

    public void atualizar(Juridico juridico) {
        juridicoRepository.update(juridico);
    }

    public void deletarPorId(Integer id) {
        juridicoRepository.deleteById(id);
    }
}
