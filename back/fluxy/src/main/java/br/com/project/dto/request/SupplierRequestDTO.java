package br.com.project.dto.request;

import lombok.Data;
import java.util.List;

@Data
public class SupplierRequestDTO {
    private String cnpj;
    private String name;
    private String street;
    private String number;
    private String neighborhood;
    private String city;
    private String cep;
    private List<String> phone;
}
