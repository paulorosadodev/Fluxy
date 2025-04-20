package br.com.project.dto.request;

public record JuridicalRequestDTO(
        String stateRegistration,
        String cnpj,
        String legalName
) {}
