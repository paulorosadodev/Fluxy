package br.com.project.dto.request;

public record ProdutoCategoriaRequestDTO(
        Integer fkProdutoId,
        String fkCategoriaCodigo
) {}
