package br.com.project.service;

import br.com.project.model.Gerente;
import br.com.project.repository.GerenteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GerenteService {

    private final GerenteRepository gerenteRepository;

    public GerenteService(GerenteRepository gerenteRepository) {
        this.gerenteRepository = gerenteRepository;
    }

    public void salvar(Gerente gerente) {
        gerenteRepository.save(gerente);
    }

    public Optional<Gerente> buscarPorId(Integer id) {
        return gerenteRepository.findById(id);
    }

    public List<Gerente> listarTodos() {
        return gerenteRepository.findAll();
    }

    public void atualizar(Gerente gerente) {
        gerenteRepository.update(gerente);
    }

    public void deletarPorId(Integer id) {
        gerenteRepository.deleteById(id);
    }
}
