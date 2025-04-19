package br.com.project.service;

import br.com.project.model.Compra;
import br.com.project.repository.CompraRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompraService {

    private final CompraRepository compraRepository;

    public CompraService(CompraRepository compraRepository) {
        this.compraRepository = compraRepository;
    }

    public void salvar(Compra compra) {
        compraRepository.save(compra);
    }

    public Optional<Compra> buscarPorNumero(Integer numero) {
        return compraRepository.findByNumero(numero);
    }

    public List<Compra> listarTodos() {
        return compraRepository.findAll();
    }

    public void atualizar(Compra compra) {
        compraRepository.update(compra);
    }

    public void deletarPorNumero(Integer numero) {
        compraRepository.deleteByNumero(numero);
    }
}
