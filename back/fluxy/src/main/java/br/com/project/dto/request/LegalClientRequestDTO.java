package br.com.project.dto.request;

import java.util.List;

public record LegalClientRequestDTO(
        String legalName,
        String cnpj,
        String stateRegistration,
        String street,
        String number,
        String neighborhood,
        String city,
        String cep,
        List<String> phone
) {}
