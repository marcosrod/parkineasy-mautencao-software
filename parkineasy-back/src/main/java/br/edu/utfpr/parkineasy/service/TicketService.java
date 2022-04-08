package br.edu.utfpr.parkineasy.service;

import br.edu.utfpr.parkineasy.dto.request.TicketRequest;
import br.edu.utfpr.parkineasy.dto.response.TicketResponse;

public interface TicketService {
    TicketResponse criarTicket(TicketRequest ticketRequest);
}
