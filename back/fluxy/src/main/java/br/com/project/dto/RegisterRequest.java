package br.com.project.dto;

import lombok.Data;

@Data
public class RegisterRequest {
    private int id;
    private String nome;
    private String senha;
}
