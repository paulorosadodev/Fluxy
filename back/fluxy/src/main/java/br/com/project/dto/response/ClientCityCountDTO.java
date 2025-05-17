package br.com.project.dto.response;

public class ClientCityCountDTO {
    private String cidade;
    private int totalClientes;

    public ClientCityCountDTO(String cidade, int totalClientes) {
        this.cidade = cidade;
        this.totalClientes = totalClientes;
    }

    public String getCidade() {
        return cidade;
    }

    public int getTotalClientes() {
        return totalClientes;
    }
}
