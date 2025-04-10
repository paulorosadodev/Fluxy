package br.com.project.dto.auth;

import br.com.project.model.Store;

public record RegisterResponseDTO(String token, Store store){
}
