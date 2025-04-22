package br.com.project.dto.request;

import java.util.List;

public record LegalClientRequestDTO(
        String corporateName,
        String cnpj,
        String stateRegistration,
        String street,
        String number,
        String neighborhood,
        String city,
        String zipCode,
        List<String> phones // se quiser usar telefone depois, já deixo preparado
) {}
