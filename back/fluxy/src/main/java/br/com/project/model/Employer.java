package br.com.project.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employer {
    private Integer idEmployee;      // ID do funcionário (PK)
    private Integer idPerson;        // ID da pessoa (FK para pessoa)
    private String employeeNumber;   // Matrícula
    private String name;             // Nome
    private String cpf;              // CPF
    private Double salary;           // Salário
    private String sectorOfActivity; // Setor
    private String workShift;        // Turno
    private String role;             // Função
    private Integer idSupervisor;    // Supervisor (FK para funcionário)

    // Adiciona os campos para relacionamento com pessoa e telefones
    private Person person;           // Dados da tabela pessoa (endereço, etc.)
    private List<String> phones;     // Telefones (lista de strings)
}