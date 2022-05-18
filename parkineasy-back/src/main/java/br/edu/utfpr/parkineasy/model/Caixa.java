package br.edu.utfpr.parkineasy.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "caixa")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Caixa {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(name = "valor")
    private Double valor;
    
    @Column(name = "data_pagamento")
    private LocalDate dataPagamento;
    
    @Column(name = "tipo_vaga")
    private Integer tipoVaga;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_pagamento", referencedColumnName = "id")
    private Pagamento pagamento;
}
