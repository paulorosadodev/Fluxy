package br.com.project.dto.response;

import lombok.Data;

@Data
public class PhysicalClientResponseDTO {
    private Integer id;
    private String name;
    private String cpf;
    private String street;
    private String number;
    private String neighborhood;
    private String city;
    private String zipCode;
}
