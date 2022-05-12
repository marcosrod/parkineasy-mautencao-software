package br.edu.utfpr.parkineasy.model;

import javax.persistence.*;

@Entity
@Table(name = "vaga")
public class Vaga {
    @Id
    private String codigo;

    private Boolean ocupada;

    @Column(name = "tipo_vaga")
    private Integer tipoVaga;

    @OneToOne(mappedBy = "vaga")
    private Pagamento pagamento;

    public Vaga() {
    }

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
