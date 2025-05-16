package br.com.project.dto.request;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class ProductSupplierRequestDTO {
    private Integer fornecedorId;
    private Integer produtoId;
    private Integer quantidadeFornecida;
    private BigDecimal valorPago;
    private LocalDate dataReposicao;
}
