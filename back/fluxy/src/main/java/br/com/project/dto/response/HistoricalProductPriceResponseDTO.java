package br.com.project.dto.response;

import java.sql.Date;

public record HistoricalProductPriceResponseDTO(
        Integer idHistoricalProductPrice,
        Date date,
        Integer price
) {}
