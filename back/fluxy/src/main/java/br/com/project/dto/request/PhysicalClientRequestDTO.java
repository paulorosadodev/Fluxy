package br.com.project.dto.request;

import java.util.List;

public record PhysicalClientRequestDTO(
        String name,
        String cpf,
        String street,
        String number,
        String neighborhood,
        String city,
        String cep,
        List<String> phone
) {}
