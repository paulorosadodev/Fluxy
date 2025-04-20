package br.com.project.service;

import br.com.project.dto.request.PhysicalClientRequestDTO;
import br.com.project.dto.response.PhysicalClientResponseDTO;
import br.com.project.model.PhysicalClient;
import br.com.project.repository.PhysicalClientRepository;
import br.com.project.util.MapperUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PhysicalClientService {

    private final PhysicalClientRepository repository;
    private final MapperUtils mapperUtils;

    public PhysicalClientService(PhysicalClientRepository repository, MapperUtils mapperUtils) {
        this.repository = repository;
        this.mapperUtils = mapperUtils;
    }

    public Integer save(PhysicalClientRequestDTO dto) {
        PhysicalClient client = mapperUtils.map(dto, PhysicalClient.class);
        return repository.save(client);
    }

    public PhysicalClientResponseDTO findById(Integer id) {
        PhysicalClient client = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Physical client not found"));
        return mapperUtils.map(client, PhysicalClientResponseDTO.class);
    }

    public List<PhysicalClientResponseDTO> findAll() {
        return mapperUtils.mapList(repository.findAll(), PhysicalClientResponseDTO.class);
    }

    public void update(Integer id, PhysicalClientRequestDTO dto) {
        PhysicalClient client = mapperUtils.map(dto, PhysicalClient.class);
        repository.update(id, client);
    }

    public void deleteById(Integer id) {
        repository.deleteById(id);
    }
}
