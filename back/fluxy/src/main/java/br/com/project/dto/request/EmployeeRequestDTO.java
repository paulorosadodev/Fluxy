package br.com.project.dto.request;

import java.util.List;

public record EmployeeRequestDTO(
        String street,
        String number,
        String neighborhood,
        String city,
        String cep,

        String cpf,
        String name,
        Integer salary,
        String sectorOfActivity,
        String workShift,
        String role,
        Integer fkSupervisor,

        List<String> phone
) {}
