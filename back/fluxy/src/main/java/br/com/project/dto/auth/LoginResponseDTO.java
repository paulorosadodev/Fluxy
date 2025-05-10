package br.com.project.dto.auth;

public class LoginResponseDTO {
    private String token;
    private String user;
    private String role;

    public LoginResponseDTO(String token, String user, String role) {
        this.token = token;
        this.user = user;
        this.role = role;
    }

    public String getToken() {
        return token;
    }
    public String getUser() {
        return user;
    }
    public String getRole() {return role;}
}
