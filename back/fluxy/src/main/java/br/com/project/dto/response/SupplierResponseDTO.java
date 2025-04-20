package br.com.project.dto.response;

public record SupplierResponseDTO(
        Integer idSupplier,
        String cnpj,
        String name
) {}
