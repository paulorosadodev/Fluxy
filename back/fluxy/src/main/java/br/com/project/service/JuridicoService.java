package br.com.project.service;

import br.com.project.dto.request.JuridicoRequestDTO;
import br.com.project.dto.response.JuridicoResponseDTO;
import br.com.project.model.Juridico;
import br.com.project.repository.JuridicoRepository;
import br.com.project.util.MapperUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JuridicoService {

    private final JuridicoRepository juridicoRepository;
    private final MapperUtils mapperUtils;

    public JuridicoService(JuridicoRepository juridicoRepository, MapperUtils mapperUtils) {
        this.juridicoRepository = juridicoRepository;
        this.mapperUtils = mapperUtils;
    }

    public void salvar(JuridicoRequestDTO juridicoRequestDTO) {
        Juridico juridico = mapperUtils.map(juridicoRequestDTO, Juridico.class);
        juridicoRepository.save(juridico);
    }

    public JuridicoResponseDTO buscarPorFkClienteId(Integer fkClienteId) {
        Juridico juridico = juridicoRepository.findByFkClienteId(fkClienteId)
                .orElseThrow(() -> new RuntimeException("Cliente Jurídico não encontrado"));
        return mapperUtils.map(juridico, JuridicoResponseDTO.class);
    }

    public List<JuridicoResponseDTO> listarTodos() {
        List<Juridico> juridicos = juridicoRepository.findAll();
        return mapperUtils.mapList(juridicos, JuridicoResponseDTO.class);
    }

    public void atualizar(Integer fkClienteId, JuridicoRequestDTO juridicoRequestDTO) {
        Juridico juridico = mapperUtils.map(juridicoRequestDTO, Juridico.class);
        juridico.setFkClienteId(fkClienteId);
        juridicoRepository.update(juridico);
    }

    public void deletar(Integer fkClienteId) {
        juridicoRepository.deleteByFkClienteId(fkClienteId);
    }
}
