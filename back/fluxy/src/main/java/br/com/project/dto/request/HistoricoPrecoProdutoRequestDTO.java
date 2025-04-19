package br.com.project.dto.request;

import java.sql.Date;

public record HistoricoPrecoProdutoRequestDTO(
        Date data,
        Integer preco
) {}
