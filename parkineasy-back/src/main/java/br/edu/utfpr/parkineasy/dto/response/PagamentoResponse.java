package br.edu.utfpr.parkineasy.dto.response;


import br.edu.utfpr.parkineasy.model.Pagamento;
import br.edu.utfpr.parkineasy.model.Vaga;
import br.edu.utfpr.parkineasy.model.enumeration.EMetodoPagamento;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
public class PagamentoResponse {
    
    private String codigoVaga;
    private LocalDateTime dataPagamento;
    private Double valor;
    private EMetodoPagamento metodoPagamento;
    
    public static PagamentoResponse convertFrom(Pagamento pagamento, Vaga vaga) {
        return PagamentoResponse.builder()
            .codigoVaga(vaga.getCodigo())
            .dataPagamento(pagamento.getDataHora())
            .metodoPagamento(pagamento.getMetodoPagamento())
            .valor(pagamento.getValor())
            .build();
    }
}
