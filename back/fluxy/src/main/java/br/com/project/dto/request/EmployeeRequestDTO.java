package br.com.project.dto.request;

import java.util.List;

public record EmployeeRequestDTO(
        // Dados de pessoa
        String street,
        String number,
        String neighborhood,
        String city,
        String cep,

        // Dados de funcionário
        String employeeNumber,
        String cpf,
        String name,
        Integer salary,
        String sectorOfActivity,
        String workShift,
        String role,
        Integer fkSupervisor,

        // Telefones
        List<String> phone
) {}
