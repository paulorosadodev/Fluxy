package br.com.project.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductSupplierResponseDTO {
    private String supplier;
    private Integer product;
    private Integer productAmount;
    private BigDecimal price;
    private LocalDate date;
}
