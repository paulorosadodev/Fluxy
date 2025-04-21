package br.com.project.dto.request;

import lombok.Data;

@Data
public class PhysicalClientRequestDTO {
    private String name;
    private String cpf;
    private String street;
    private String number;
    private String neighborhood;
    private String city;
    private String zipCode;
}
