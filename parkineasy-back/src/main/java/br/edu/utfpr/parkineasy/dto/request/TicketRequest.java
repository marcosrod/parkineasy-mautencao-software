package br.edu.utfpr.parkineasy.dto.request;

import javax.validation.constraints.NotBlank;

public class TicketRequest {
    @NotBlank
    private String codigoVaga;

    public TicketRequest() {
    }

    public TicketRequest(String codigoVaga) {
        this.codigoVaga = codigoVaga;
    }

    public String getCodigoVaga() {
        return codigoVaga;
    }
}
