package br.com.project.dto.response;

public record PhysiqueResponseDTO(
        Integer fkClientId,
        String name,
        String cpf
) {}
