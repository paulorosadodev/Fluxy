package br.com.project.dto.response;

public record FuncionarioResponseDTO(
        Integer idFuncionario,
        String matricula,
        String cpf,
        String nome,
        Integer salario,
        Integer idSupervisor
) {}
