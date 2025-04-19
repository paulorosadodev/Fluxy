package br.com.project.dto.request;

public record FuncionarioRequestDTO(
        String matricula,
        String cpf,
        String nome,
        Integer salario,
        Integer idSupervisor
) {}
