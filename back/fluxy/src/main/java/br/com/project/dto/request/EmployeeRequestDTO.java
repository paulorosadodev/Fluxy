package br.com.project.dto.request;

public record EmployeeRequestDTO(
        String employeeNumber,
        String cpf,
        String name,
        Integer salary,
        String role,

        Integer FKSupervisor
) {}
