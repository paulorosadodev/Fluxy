package br.com.project.dto.response;

import java.sql.Date;

public record HistoricPriceProductResponseDTO(
        Integer idHistoricalProductPrice,
        Date date,
        Integer price
) {}
