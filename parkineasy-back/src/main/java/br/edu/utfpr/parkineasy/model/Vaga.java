package br.edu.utfpr.parkineasy.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "vaga")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Vaga {
    @Id
    private String codigo;

    private Boolean ocupada;

    @Column(name = "tipo_vaga")
    private Integer tipoVaga;
    
    @OneToMany(mappedBy = "vaga")
    private List<Ticket> tickets;

    public Vaga(String codigo, Integer tipoVaga) {
        this.codigo = codigo;
        this.tipoVaga = tipoVaga;
        this.ocupada = false;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Boolean getOcupada() {
        return ocupada;
    }

    public void setOcupada(Boolean ocupada) {
        this.ocupada = ocupada;
    }

    public Integer getTipoVaga() {
        return tipoVaga;
    }

    public void setTipoVaga(Integer tipoVaga) {
        this.tipoVaga = tipoVaga;
    }
}
