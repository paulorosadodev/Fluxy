package br.com.project.service;

import br.com.project.model.Operacional;
import br.com.project.repository.OperacionalRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OperacionalService {

    private final OperacionalRepository operacionalRepository;

    public OperacionalService(OperacionalRepository operacionalRepository) {
        this.operacionalRepository = operacionalRepository;
    }

    public void salvar(Operacional operacional) {
        operacionalRepository.save(operacional);
    }

    public Optional<Operacional> buscarPorId(Integer id) {
        return operacionalRepository.findById(id);
    }

    public List<Operacional> listarTodos() {
        return operacionalRepository.findAll();
    }

    public void atualizar(Operacional operacional) {
        operacionalRepository.update(operacional);
    }

    public void deletarPorId(Integer id) {
        operacionalRepository.deleteById(id);
    }
}
