package br.com.project.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeResponseDTO {
    private Integer id;
    private String employeeNumber;
    private String cpf;
    private String name;
    private Integer salary;
    private String sectorOfActivity;
    private String workShift;
    private String role;
    private Integer idSupervisor;
    private String street;
    private String number;
    private String neighborhood;
    private String city;
    private String cep;
    private String[] phone;
}
