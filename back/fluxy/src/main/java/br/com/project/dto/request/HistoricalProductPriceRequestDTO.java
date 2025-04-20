package br.com.project.dto.request;

import java.sql.Date;

public record HistoricalProductPriceRequestDTO(
        Date date,
        Integer price
) {}
