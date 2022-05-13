package br.edu.utfpr.parkineasy.service.impl;

import br.edu.utfpr.parkineasy.dto.request.TicketRequest;
import br.edu.utfpr.parkineasy.dto.response.TicketResponse;
import br.edu.utfpr.parkineasy.exception.ParkineasyException;
import br.edu.utfpr.parkineasy.model.Ticket;
import br.edu.utfpr.parkineasy.repository.TicketRepository;
import br.edu.utfpr.parkineasy.repository.VagaRepository;
import br.edu.utfpr.parkineasy.service.TicketService;
import br.edu.utfpr.parkineasy.service.VagaService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TicketServiceImpl implements TicketService {
    private final VagaService vagaService;
    
    private final VagaRepository vagaRepository;
    private final TicketRepository ticketRepository;

    public TicketServiceImpl(VagaService vagaService, TicketRepository ticketRepository, VagaRepository vagaRepository) {
        this.vagaService = vagaService;
        this.ticketRepository = ticketRepository;
        this.vagaRepository = vagaRepository;
    }

    public TicketResponse criarTicket(TicketRequest ticketRequest) {
        var vaga = vagaRepository.findById(ticketRequest.getCodigoVaga())
            .orElseThrow(() -> new ParkineasyException("Vaga não encontrada."));
        Ticket ticket = new Ticket();
        ticket.setVaga(vaga);
        ticket.setDataHora(LocalDateTime.now());
        ticket = ticketRepository.save(ticket);
        vagaService.ocuparVaga(ticketRequest.getCodigoVaga());
        return new TicketResponse(ticket);
    }
}
