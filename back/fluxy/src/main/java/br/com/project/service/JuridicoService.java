package br.com.project.service;

import br.com.project.dto.request.JuridicalRequestDTO;
import br.com.project.dto.response.JuridicalResponseDTO;
import br.com.project.model.Juridical;
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

    public void salvar(JuridicalRequestDTO juridicoRequestDTO) {
        Juridical juridico = mapperUtils.map(juridicoRequestDTO, Juridical.class);
        juridicoRepository.save(juridico);
    }

    public JuridicalResponseDTO buscarPorFkClienteId(Integer fkClienteId) {
        Juridical juridico = juridicoRepository.findByFkClienteId(fkClienteId)
                .orElseThrow(() -> new RuntimeException("Cliente Jurídico não encontrado"));
        return mapperUtils.map(juridico, JuridicalResponseDTO.class);
    }

    public List<JuridicalResponseDTO> listarTodos() {
        List<Juridical> juridicos = juridicoRepository.findAll();
        return mapperUtils.mapList(juridicos, JuridicalResponseDTO.class);
    }

    public void atualizar(Integer fkClienteId, JuridicalRequestDTO juridicoRequestDTO) {
        Juridical juridico = mapperUtils.map(juridicoRequestDTO, Juridical.class);
        juridico.setFkClienteId(fkClienteId);
        juridicoRepository.update(juridico);
    }

    public void deletar(Integer fkClienteId) {
        juridicoRepository.deleteByFkClienteId(fkClienteId);
    }
}
