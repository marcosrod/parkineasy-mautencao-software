package br.edu.utfpr.parkineasy.dto.request;

import br.edu.utfpr.parkineasy.model.enumeration.EMetodoPagamento;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PagamentoRequest {
    
    @NotNull
    @JsonProperty("ticketId")
    private Long ticketId;
    
    @NotNull
    @JsonProperty("vagaId")
    private String vagaId;
    
    @NotNull
    @JsonProperty("metodoPagamento")
    private EMetodoPagamento metodoPagamento;
}
