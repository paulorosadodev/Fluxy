package br.com.project.dto.response;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ClientSpendingDTO {
    private Integer clientId;
    private String name;
    private BigDecimal totalSpending;

    public ClientSpendingDTO(Integer clientId, String name, BigDecimal totalSpending) {
        this.clientId = clientId;
        this.name = name;
        this.totalSpending = totalSpending;
    }
}
