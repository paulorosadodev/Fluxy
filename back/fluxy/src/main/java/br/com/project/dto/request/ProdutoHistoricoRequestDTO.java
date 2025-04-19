package br.com.project.dto.request;

public record ProdutoHistoricoRequestDTO(
        Integer fkProdutoId,
        Integer fkHistoricoPrecoProdutoId
) {}
