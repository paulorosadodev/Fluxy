package br.com.project.dto.request;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class ProductSupplierRequestDTO {
    private String supplier;
    private String product;
    private Integer productAmount;
    private BigDecimal price;
    private LocalDate date;
}
