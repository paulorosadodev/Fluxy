package br.com.project.service;

import br.com.project.dto.request.PurchaseRequestDTO;
import br.com.project.dto.response.PurchaseResponseDTO;
import br.com.project.model.Purchase;
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

    public void salvar(PurchaseRequestDTO compraRequestDTO) {
        Purchase compra = mapperUtils.map(compraRequestDTO, Purchase.class);
        compraRepository.save(compra);
    }

    public PurchaseResponseDTO buscarPorNumero(Integer numero) {
        Purchase compra = compraRepository.findByNumero(numero)
                .orElseThrow(() -> new RuntimeException("Compra não encontrada"));
        return mapperUtils.map(compra, PurchaseResponseDTO.class);
    }

    public List<PurchaseResponseDTO> listarTodas() {
        List<Purchase> compras = compraRepository.findAll();
        return mapperUtils.mapList(compras, PurchaseResponseDTO.class);
    }

    public void atualizar(Integer numero, PurchaseRequestDTO compraRequestDTO) {
        Purchase compra = mapperUtils.map(compraRequestDTO, Purchase.class);
        compra.setNumero(numero);
        compraRepository.update(compra);
    }

    public void deletar(Integer numero) {
        compraRepository.deleteByNumero(numero);
    }
}
