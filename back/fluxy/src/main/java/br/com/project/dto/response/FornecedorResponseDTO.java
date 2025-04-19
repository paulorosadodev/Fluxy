package br.com.project.dto.response;

public record FornecedorResponseDTO(
        Integer idFornecedor,
        String cnpj,
        String nome
) {}
