package br.com.project.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LegalClientResponseDTO {
    private Integer id;
    private String legalName;
    private String cnpj;
    private String stateRegistration;
    private String street;
    private String number;
    private String neighborhood;
    private String city;
    private String cep;
    private String[] phone;
}