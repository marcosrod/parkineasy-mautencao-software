package br.edu.utfpr.parkineasy.service;

import br.edu.utfpr.parkineasy.dto.request.PagamentoRequest;
import br.edu.utfpr.parkineasy.model.Pagamento;
import br.edu.utfpr.parkineasy.model.Ticket;
import br.edu.utfpr.parkineasy.model.Vaga;
import br.edu.utfpr.parkineasy.model.enumeration.EMetodoPagamento;
import br.edu.utfpr.parkineasy.repository.PagamentoRepository;
import br.edu.utfpr.parkineasy.repository.TicketRepository;
import br.edu.utfpr.parkineasy.repository.VagaRepository;
import br.edu.utfpr.parkineasy.service.impl.PagamentoServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PagamentoServiceTest {
    
    @Mock
    private TicketRepository ticketRepository;
    
    @Mock
    private VagaRepository vagaRepository;
    
    private PagamentoService pagamentoService;

    @BeforeEach
    void setUp() {
        pagamentoService = new PagamentoServiceImpl();
    }

    private static Pagamento getPagamentoOf(Long id, LocalDateTime dataHora, Double valor,
                                            EMetodoPagamento metodoPagamento, String codigoVaga, Long idTicket) {
        return Pagamento.builder()
            .id(id)
            .dataHora(LocalDateTime.now())
            .valor(valor)
            .metodoPagamento(metodoPagamento)
            .vaga(Vaga.builder().codigo(codigoVaga).build())
            .ticket(Ticket.builder().id(idTicket).build())
            .build();
    }
    
    private static PagamentoRequest getPagamentoRequestOf(Long ticketId, String vagaId, Double valor, 
                                                          EMetodoPagamento metodoPagamento) {
        return PagamentoRequest.builder()
            .ticketId(ticketId)
            .vagaId(vagaId)
            .valor(valor)
            .metodoPagamento(metodoPagamento)
            .build();
    }
    
    @Test
    public void pagarTicket_deveRetornarComprovantePagamento_sePagamentoOk() {
        when(ticketRepository.findById(1L)).thenReturn(Optional.of(Ticket.builder().id(1L).build()));
        when(vagaRepository.findById("A01")).thenReturn(Optional.of(Vaga.builder().codigo("A01").build()));
        var pagamentoRequest = getPagamentoRequestOf(1L, "A01", 15.20, 
            EMetodoPagamento.CARTAO);
        assertThat(pagamentoService.pagarTicket(pagamentoRequest))
            .extracting("codigoVaga", "valor", "metodoPagamento")
            .containsExactly("A02", 15.20, EMetodoPagamento.CARTAO);
    }
}