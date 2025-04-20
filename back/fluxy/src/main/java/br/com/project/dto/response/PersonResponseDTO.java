package br.com.project.dto.response;

public record PersonResponseDTO(
        Integer idPerson,
        String street,
        String number,
        String neighborhood,
        String city,
        String cep
) {}
