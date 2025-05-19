package br.com.project.dto.request;

import java.util.List;

public record SupplierRequestDTO(
        String cnpj,
        String name,
        String street,
        String number,
        String neighborhood,
        String city,
        String cep,
        List<String> phone
) {}