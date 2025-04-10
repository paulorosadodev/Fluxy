package br.com.project.dto.auth;

import br.com.project.model.Loja;

public record ResponseDTO (String token, Loja loja){
}
