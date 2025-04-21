package br.com.project.dto.response;

import lombok.Data;

@Data
public class LegalClientResponseDTO {
    private Integer id;
    private String corporateName;
    private String cnpj;
    private String stateRegistration;
    private String street;
    private String number;
    private String neighborhood;
    private String city;
    private String zipCode;
}
