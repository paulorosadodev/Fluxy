package br.com.project.dto.request;

import lombok.Data;

@Data
public class PurchaseCountByMonthAndYearRequestDTO {
    private int month;
    private int year;
}
