package br.com.project.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeResponseDTO {
    private Integer idEmployee;        // agora o nome combina com o da entidade
    private String employeeNumber;
    private String cpf;
    private String name;
    private Integer salary;
    private String sectorOfActivity;
    private String workShift;
    private String role;
    private Integer idSupervisor;
}
