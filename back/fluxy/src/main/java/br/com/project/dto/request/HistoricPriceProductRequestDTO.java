package br.com.project.dto.request;

import java.sql.Date;

public record HistoricPriceProductRequestDTO(
        Date date,
        Integer price
) {}
