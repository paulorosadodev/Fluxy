package br.com.project.service;

import br.com.project.dto.request.LegalClientRequestDTO;
import br.com.project.dto.response.LegalClientResponseDTO;
import br.com.project.model.LegalClient;
import br.com.project.repository.LegalClientRepository;
import br.com.project.util.MapperUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LegalClientService {

    private final LegalClientRepository repository;
    private final MapperUtils mapperUtils;

    public LegalClientService(LegalClientRepository repository, MapperUtils mapperUtils) {
        this.repository = repository;
        this.mapperUtils = mapperUtils;
    }

    public Integer save(LegalClientRequestDTO dto) {
        LegalClient client = mapperUtils.map(dto, LegalClient.class);
        return repository.save(client);
    }

    public LegalClientResponseDTO findById(Integer id) {
        LegalClient client = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Legal client not found"));
        return mapperUtils.map(client, LegalClientResponseDTO.class);
    }

    public List<LegalClientResponseDTO> findAll() {
        return mapperUtils.mapList(repository.findAll(), LegalClientResponseDTO.class);
    }

    public void update(Integer id, LegalClientRequestDTO dto) {
        LegalClient client = mapperUtils.map(dto, LegalClient.class);
        repository.update(id, client);
    }

    public void deleteById(Integer id) {
        repository.deleteById(id);
    }
}
