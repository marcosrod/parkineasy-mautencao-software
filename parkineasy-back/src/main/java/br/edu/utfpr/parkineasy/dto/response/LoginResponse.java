package br.edu.utfpr.parkineasy.dto.response;

public class LoginResponse {
    private final String authenticationToken;
    private final String usuario;

    public LoginResponse(String authenticationToken, String usuario) {
        this.authenticationToken = authenticationToken;
        this.usuario = usuario;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getAuthenticationToken() {
        return authenticationToken;
    }
}
