package br.com.project.dto.response;

public record PessoaResponseDTO(
        Integer idPessoa,
        String rua,
        String numero,
        String bairro,
        String cidade,
        String cep
) {}
