package br.com.project.dto.auth;

public class LoginResponseDTO {
    private String token;
    private String store;

    public LoginResponseDTO(String token, String store) {
        this.token = token;
        this.store = store;
    }

    public String getToken() {
        return token;
    }
    public String getStore() {
        return store;
    }
}
