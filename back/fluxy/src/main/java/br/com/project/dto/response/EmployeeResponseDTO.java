package br.com.project.dto.response;

public record EmployeeResponseDTO(
        Integer idFunctionary,
        String employeeNumber,
        String cpf,
        String name,
        Integer salary,

        Integer idSupervisor
) {}
