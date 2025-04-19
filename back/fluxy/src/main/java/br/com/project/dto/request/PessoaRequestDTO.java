package br.com.project.dto.request;

public record PessoaRequestDTO(
        String rua,
        String numero,
        String bairro,
        String cidade,
        String cep
) {}
