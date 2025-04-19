package br.com.project.dto.response;

import java.sql.Date;

public record HistoricoPrecoProdutoResponseDTO(
        Integer idHistoricoPrecoProduto,
        Date data,
        Integer preco
) {}
