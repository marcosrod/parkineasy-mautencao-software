package br.edu.utfpr.parkineasy.dto.request;

import br.edu.utfpr.parkineasy.model.enumeration.EMetodoPagamento;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@Builder
public class PagamentoRequest {
    
    @NotNull
    private Long ticketId;
    
    @NotNull
    private String vagaId;
    
    @NotNull
    private Double valor;
    
    @NotNull
    private EMetodoPagamento metodoPagamento;
}
