package br.edu.utfpr.parkineasy.dto.response;

import br.edu.utfpr.parkineasy.model.Funcionario;

public class FuncionarioResponse {
    private final Long id;
    private final String nome;
    private final String email;
    private final String usuario;

    public FuncionarioResponse(Funcionario funcionario) {
        this.id = funcionario.getId();
        this.nome = funcionario.getNome();
        this.email = funcionario.getEmail();
        this.usuario = funcionario.getUsuario();
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getUsuario() {
        return usuario;
    }
}
