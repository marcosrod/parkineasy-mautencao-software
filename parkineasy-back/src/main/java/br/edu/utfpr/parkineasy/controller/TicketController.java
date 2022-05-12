package br.edu.utfpr.parkineasy.controller;

import br.edu.utfpr.parkineasy.dto.request.PagamentoRequest;
import br.edu.utfpr.parkineasy.dto.request.TicketRequest;
import br.edu.utfpr.parkineasy.dto.response.PagamentoResponse;
import br.edu.utfpr.parkineasy.dto.response.TicketResponse;
import br.edu.utfpr.parkineasy.service.PagamentoService;
import br.edu.utfpr.parkineasy.service.TicketService;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/tickets")
public class TicketController {
    
    private final PagamentoService pagamentoService;
    
    private final TicketService ticketService;

    public TicketController(TicketService ticketService, PagamentoService pagamentoService) {
        this.ticketService = ticketService;
        this.pagamentoService = pagamentoService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TicketResponse criarTicket(@RequestBody @Valid TicketRequest ticketRequest) {
        return ticketService.criarTicket(ticketRequest);
    }
    
    @PostMapping("pagar")
    public PagamentoResponse pagarTicket(@RequestBody @Valid PagamentoRequest pagamentoRequest) {
        return pagamentoService.pagarTicket(pagamentoRequest);
    }
}
