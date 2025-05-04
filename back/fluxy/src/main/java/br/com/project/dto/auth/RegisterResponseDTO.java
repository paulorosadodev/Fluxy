package br.com.project.dto.auth;

import br.com.project.model.User;

public record RegisterResponseDTO(String token, User user){
}
