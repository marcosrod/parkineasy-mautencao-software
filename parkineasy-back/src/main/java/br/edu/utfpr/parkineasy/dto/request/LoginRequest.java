package br.edu.utfpr.parkineasy.dto.request;

import javax.validation.constraints.NotBlank;

public class LoginRequest {
    @NotBlank
    private String usuario;

    @NotBlank
    private String senha;

    public LoginRequest() {
    }

    public LoginRequest(String usuario, String senha) {
        this.usuario = usuario;
        this.senha = senha;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getSenha() {
        return senha;
    }
}
