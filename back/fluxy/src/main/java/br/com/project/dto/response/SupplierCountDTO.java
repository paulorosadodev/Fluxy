package br.com.project.dto.response;

import lombok.Data;

@Data
public class SupplierCountDTO {
    private int total;
    public SupplierCountDTO(int total) {
        this.total = total;
    }
}
