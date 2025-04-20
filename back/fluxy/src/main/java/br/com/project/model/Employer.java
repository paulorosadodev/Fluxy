package br.com.project.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employer {
    private Integer idEmployee;
    private Integer idPerson; // FK para Pessoa
    private String employeeNumber;
    private String name;
    private String cpf;
    private Double salary;
    private String sectorOfActivity;
    private String workShift;
    private String role;
    private Integer idSupervisor; // FK para outro Funcionário
}
