package br.com.project.service;

import br.com.project.dto.request.HistoricPriceProductRequestDTO;
import br.com.project.dto.response.HistoricPriceProductResponseDTO;
import br.com.project.model.HistoricPriceProduct;
import br.com.project.repository.HistoricPriceProductRepository;
import br.com.project.util.MapperUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class HistoricPriceProductService {

    private final HistoricPriceProductRepository historicPriceProductRepository;
    private final MapperUtils mapperUtils;

    public HistoricPriceProductService(HistoricPriceProductRepository historicPriceProductRepository, MapperUtils mapperUtils) {
        this.historicPriceProductRepository = historicPriceProductRepository;
        this.mapperUtils = mapperUtils;
    }

    @Transactional
    public HistoricPriceProductResponseDTO save(HistoricPriceProductRequestDTO requestDTO) {
        try {
            HistoricPriceProduct historic = mapperUtils.map(requestDTO, HistoricPriceProduct.class);
            Integer id = historicPriceProductRepository.save(historic);
            historic.setIdHistoricPriceProduct(id);
            return mapperUtils.map(historic, HistoricPriceProductResponseDTO.class);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public List<HistoricPriceProductResponseDTO> findAll() {
        try {
            return mapperUtils.mapList(historicPriceProductRepository.findAll(), HistoricPriceProductResponseDTO.class);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public HistoricPriceProductResponseDTO findById(Integer id) {
        try {
            HistoricPriceProduct historic = historicPriceProductRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Histórico de preço com ID " + id + " não encontrado."));
            return mapperUtils.map(historic, HistoricPriceProductResponseDTO.class);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Transactional
    public HistoricPriceProductResponseDTO update(Integer id, HistoricPriceProductRequestDTO requestDTO) {
        try {
            HistoricPriceProduct historic = mapperUtils.map(requestDTO, HistoricPriceProduct.class);
            historic.setIdHistoricPriceProduct(id);
            historicPriceProductRepository.update(historic);
            return mapperUtils.map(historic, HistoricPriceProductResponseDTO.class);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Transactional
    public void delete(Integer id) {
        try {
            historicPriceProductRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
