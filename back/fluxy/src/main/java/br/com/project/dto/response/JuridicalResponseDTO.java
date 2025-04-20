package br.com.project.dto.response;

public record JuridicalResponseDTO(
        Integer fkClienteId,
        String stateRegistration,
        String cnpj,
        String legalName
) {}
