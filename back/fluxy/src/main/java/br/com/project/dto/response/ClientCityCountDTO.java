package br.com.project.dto.response;

import lombok.Data;

@Data
public class ClientCityCountDTO {
    private String cidade;
    private int totalClientes;

    public ClientCityCountDTO(String cidade, int totalClientes) {
        this.cidade = cidade;
        this.totalClientes = totalClientes;
    }
}
