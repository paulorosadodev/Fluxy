package br.com.project.dto.response;

public record FisicoResponseDTO(
        Integer fkClienteId,
        String nome,
        String cpf
) {}
