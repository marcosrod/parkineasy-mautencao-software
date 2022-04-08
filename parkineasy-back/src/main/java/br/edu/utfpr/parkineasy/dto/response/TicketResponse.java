package br.edu.utfpr.parkineasy.dto.response;

import br.edu.utfpr.parkineasy.model.Ticket;

public class TicketResponse {
    private final Long id;
    private final String codigoVaga;
    private final String dataHora;

    public TicketResponse(Ticket ticket) {
        id = ticket.getId();
        codigoVaga = ticket.getCodigoVaga();
        dataHora = ticket.getDataHora().toString();
    }

    public Long getId() {
        return id;
    }

    public String getCodigoVaga() {
        return codigoVaga;
    }

    public String getDataHora() {
        return dataHora;
    }
}
