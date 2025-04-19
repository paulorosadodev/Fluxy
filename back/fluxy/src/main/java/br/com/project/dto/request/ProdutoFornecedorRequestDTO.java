package br.com.project.dto.request;

public record ProdutoFornecedorRequestDTO(
        Integer fkFornecedorId,
        Integer fkProdutoId
) {}
