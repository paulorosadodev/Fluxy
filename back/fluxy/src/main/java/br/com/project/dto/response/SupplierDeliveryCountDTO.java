package br.com.project.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SupplierDeliveryCountDTO {

    private Integer id;
    private String name;
    private String cnpj;
    private Integer totalEntregas;

}
