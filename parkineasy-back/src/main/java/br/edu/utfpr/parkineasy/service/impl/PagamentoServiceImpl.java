package br.edu.utfpr.parkineasy.service.impl;

import br.edu.utfpr.parkineasy.dto.request.PagamentoRequest;
import br.edu.utfpr.parkineasy.dto.response.PagamentoResponse;
import br.edu.utfpr.parkineasy.exception.ParkineasyException;
import br.edu.utfpr.parkineasy.model.Pagamento;
import br.edu.utfpr.parkineasy.repository.PagamentoRepository;
import br.edu.utfpr.parkineasy.repository.TicketRepository;
import br.edu.utfpr.parkineasy.repository.VagaRepository;
import br.edu.utfpr.parkineasy.service.PagamentoService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PagamentoServiceImpl implements PagamentoService {
    private final TicketRepository ticketRepository;
    private final VagaRepository vagaRepository;
    private final PagamentoRepository pagamentoRepository;

    public PagamentoServiceImpl(TicketRepository ticketRepository, VagaRepository vagaRepository, PagamentoRepository pagamentoRepository) {
        this.ticketRepository = ticketRepository;
        this.vagaRepository = vagaRepository;
        this.pagamentoRepository = pagamentoRepository;
    }

    @Override
    public PagamentoResponse pagarTicket(PagamentoRequest pagamentoRequest) {
        var ticket = ticketRepository.findById(pagamentoRequest.getTicketId())
            .orElseThrow(() -> new ParkineasyException("Ticket Não Encontrado."));
        var vaga = vagaRepository.findById(ticket.getVaga().getCodigo())
            .orElseThrow(() -> new ParkineasyException("Vaga Não Encontrada."));
        var pagamento = Pagamento.builder()
            .dataHora(LocalDateTime.now())
            .ticket(ticket)
            .metodoPagamento(pagamentoRequest.getMetodoPagamento())
            .valor(pagamentoRequest.getValor())
            .build();
        var pagamentoRealizado = pagamentoRepository.save(pagamento);
        vaga.setOcupada(false);
        vagaRepository.save(vaga);
        
        return PagamentoResponse.convertFrom(pagamentoRealizado, vaga);
    }
    
    public Double calcularValor(Long ticketId) {
        var ticket = ticketRepository.findById(ticketId)
            .orElseThrow(() -> new ParkineasyException("Ticket Não Encontrado."));
        var horarioTicket = ticket.getDataHora().toLocalTime().toSecondOfDay();
        var horarioAtual = LocalDateTime.now().toLocalTime().toSecondOfDay();
        var horasGastas = ((horarioAtual-horarioTicket) / 60) / 60;
        
        return (double) (5 * horasGastas);
    }
}
