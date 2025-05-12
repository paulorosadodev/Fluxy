package br.com.project.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employer {
    private Integer idEmployee;
    private Integer idPerson;
    private String employeeNumber;
    private String name;
    private String cpf;
    private Double salary;
    private String sectorOfActivity;
    private String workShift;
    private String role;
    private Integer idSupervisor;

    private Person person;
    private List<String> phones;
}