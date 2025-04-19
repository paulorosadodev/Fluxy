package br.com.project.dto.response;

public record JuridicoResponseDTO(
        Integer fkClienteId,
        String inscrEstadual,
        String cnpj,
        String razaoSocial
) {}
