package br.com.project.dto;

import lombok.Data;

@Data
public class LoginRequest {
    private String nome;
    private String senha;
}
