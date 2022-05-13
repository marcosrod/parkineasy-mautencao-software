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
        var vaga = vagaRepository.findById(pagamentoRequest.getVagaId())
            .orElseThrow(() -> new ParkineasyException("Vaga Não Encontrada."));
        var pagamento = Pagamento.builder()
            .dataHora(LocalDateTime.now())
            .ticket(ticket)
            .vaga(vaga)
            .metodoPagamento(pagamentoRequest.getMetodoPagamento())
            .valor(pagamentoRequest.getValor())
            .build();
        var pagamentoRealizado = pagamentoRepository.save(pagamento);
        vaga.setOcupada(false);
        vagaRepository.save(vaga);
        
        return PagamentoResponse.convertFrom(pagamentoRealizado);
    }
}
