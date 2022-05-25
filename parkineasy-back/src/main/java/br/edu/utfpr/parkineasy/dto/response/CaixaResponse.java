package br.edu.utfpr.parkineasy.dto.response;

import br.edu.utfpr.parkineasy.model.enumeration.TipoVaga;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CaixaResponse {
    private Double totalCaixa;
    private LocalDate dataReferencia;
    private TipoVaga tipoVaga;
}
