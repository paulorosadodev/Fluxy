package br.com.project.dto.response;

public record ProdutoHistoricoResponseDTO(
        Integer fkProdutoId,
        Integer fkHistoricoPrecoProdutoId
) {}
