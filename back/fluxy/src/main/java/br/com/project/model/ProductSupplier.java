package br.com.project.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductSupplier {
    private Integer id;
    private Integer supplier;
    private Integer product;
    private Integer productAmount;
    private BigDecimal price;
    private LocalDate date;

}
