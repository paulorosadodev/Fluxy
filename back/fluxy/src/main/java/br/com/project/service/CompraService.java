package br.com.project.service;

import br.com.project.dto.request.CompraRequestDTO;
import br.com.project.dto.response.CompraResponseDTO;
import br.com.project.model.Compra;
import br.com.project.repository.CompraRepository;
import br.com.project.util.MapperUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompraService {

    private final CompraRepository compraRepository;
    private final MapperUtils mapperUtils;

    public CompraService(CompraRepository compraRepository, MapperUtils mapperUtils) {
        this.compraRepository = compraRepository;
        this.mapperUtils = mapperUtils;
    }

    public void salvar(CompraRequestDTO compraRequestDTO) {
        Compra compra = mapperUtils.map(compraRequestDTO, Compra.class);
        compraRepository.save(compra);
    }

    public CompraResponseDTO buscarPorNumero(Integer numero) {
        Compra compra = compraRepository.findByNumero(numero)
                .orElseThrow(() -> new RuntimeException("Compra não encontrada"));
        return mapperUtils.map(compra, CompraResponseDTO.class);
    }

    public List<CompraResponseDTO> listarTodas() {
        List<Compra> compras = compraRepository.findAll();
        return mapperUtils.mapList(compras, CompraResponseDTO.class);
    }

    public void atualizar(Integer numero, CompraRequestDTO compraRequestDTO) {
        Compra compra = mapperUtils.map(compraRequestDTO, Compra.class);
        compra.setNumero(numero);
        compraRepository.update(compra);
    }

    public void deletar(Integer numero) {
        compraRepository.deleteByNumero(numero);
    }
}
