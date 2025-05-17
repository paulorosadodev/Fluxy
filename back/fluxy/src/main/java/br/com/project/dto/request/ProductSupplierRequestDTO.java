package br.com.project.dto.request;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class ProductSupplierRequestDTO {
    private Integer supplierId;
    private Integer productId;
    private Integer productAmount;
    private BigDecimal price;
}
