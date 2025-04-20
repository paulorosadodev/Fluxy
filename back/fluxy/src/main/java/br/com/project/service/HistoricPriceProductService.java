package br.com.project.service;

import br.com.project.dto.request.HistoricPriceProductRequestDTO;
import br.com.project.dto.response.HistoricPriceProductResponseDTO;
import br.com.project.model.HistoricPriceProduct;
import br.com.project.repository.HistoricPriceProductRepository;
import br.com.project.util.MapperUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HistoricPriceProductService {

    private final HistoricPriceProductRepository historicPriceProductRepository;
    private final MapperUtils mapperUtils;

    public HistoricPriceProductService(HistoricPriceProductRepository historicPriceProductRepository, MapperUtils mapperUtils) {
        this.historicPriceProductRepository = historicPriceProductRepository;
        this.mapperUtils = mapperUtils;
    }

    public HistoricPriceProductResponseDTO save(HistoricPriceProductRequestDTO requestDTO) {
        HistoricPriceProduct historic = mapperUtils.map(requestDTO, HistoricPriceProduct.class);
        Integer id = historicPriceProductRepository.save(historic);
        historic.setIdHistoricPriceProduct(id);
        return mapperUtils.map(historic, HistoricPriceProductResponseDTO.class);
    }

    public List<HistoricPriceProductResponseDTO> findAll() {
        return mapperUtils.mapList(historicPriceProductRepository.findAll(), HistoricPriceProductResponseDTO.class);
    }

    public HistoricPriceProductResponseDTO findById(Integer id) {
        HistoricPriceProduct historic = historicPriceProductRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Historic Price not found"));
        return mapperUtils.map(historic, HistoricPriceProductResponseDTO.class);
    }

    public HistoricPriceProductResponseDTO update(Integer id, HistoricPriceProductRequestDTO requestDTO) {
        HistoricPriceProduct historic = mapperUtils.map(requestDTO, HistoricPriceProduct.class);
        historic.setIdHistoricPriceProduct(id);
        historicPriceProductRepository.update(historic);
        return mapperUtils.map(historic, HistoricPriceProductResponseDTO.class);
    }

    public void delete(Integer id) {
        historicPriceProductRepository.deleteById(id);
    }
}
