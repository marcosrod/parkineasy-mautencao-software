package br.edu.utfpr.parkineasy.dto.request;

import javax.validation.constraints.NotBlank;

public class FuncionarioRequest {
    @NotBlank
    private String nome;

    @NotBlank
    private String email;

    @NotBlank
    private String senha;

    @NotBlank
    private String usuario;

    public FuncionarioRequest() {
    }

    public FuncionarioRequest(String nome, String email, String senha, String usuario) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.usuario = usuario;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getSenha() {
        return senha;
    }

    public String getUsuario() {
        return usuario;
    }
}
