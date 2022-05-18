package br.edu.utfpr.parkineasy.model;

import br.edu.utfpr.parkineasy.model.enumeration.EMetodoPagamento;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "Pagamento")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Pagamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "DATA_HORA")
    private LocalDateTime dataHora; 
    
    @Column(name = "VALOR")
    private Double valor;
    
    @Column(name = "METODO_PAGAMENTO")
    private EMetodoPagamento metodoPagamento;
    
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ID_TICKET", referencedColumnName = "id")
    private Ticket ticket;

    @OneToOne(mappedBy = "pagamento")
    private Caixa caixa;
}
