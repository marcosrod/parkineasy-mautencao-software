package br.edu.utfpr.parkineasy.service;

import br.edu.utfpr.parkineasy.dto.request.PagamentoRequest;
import br.edu.utfpr.parkineasy.dto.response.PagamentoResponse;
import br.edu.utfpr.parkineasy.exception.ParkineasyException;
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
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PagamentoServiceTest {
    
    @Mock
    private TicketRepository ticketRepository;
    
    @Mock
    private VagaRepository vagaRepository;
    
    @Mock
    private PagamentoRepository pagamentoRepository;
    
    private PagamentoService pagamentoService;

    @BeforeEach
    void setUp() {
        pagamentoService = new PagamentoServiceImpl(ticketRepository, vagaRepository, pagamentoRepository);
    }

    private static Pagamento getPagamentoOf(Long id, Double valor,
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
    
    private static PagamentoRequest getPagamentoRequestOf(Long ticketId, String vagaId, 
                                                          EMetodoPagamento metodoPagamento) {
        return PagamentoRequest.builder()
            .ticketId(ticketId)
            .vagaId(vagaId)
            .metodoPagamento(metodoPagamento)
            .build();
    }
    
    @Test
    public void pagarTicket_deveRetornarComprovantePagamento_sePagamentoOk() {
        var dataHora = LocalDateTime.of(2022, 10, 5, 18, 10, 05);
        when(ticketRepository.findById(1L)).thenReturn(Optional.of(Ticket.builder().id(1L).dataHora(dataHora).build()));
        when(vagaRepository.findById("A01")).thenReturn(Optional.of(Vaga.builder().codigo("A01").build()));
        when(pagamentoRepository.save(any())).thenReturn(getPagamentoOf(1L, 15.20, 
            EMetodoPagamento.CARTAO, "A01", 1L));
        
        var pagamentoRequest = getPagamentoRequestOf(1L, "A01", 
            EMetodoPagamento.CARTAO);
        
        assertThat(pagamentoService.pagarTicket(pagamentoRequest))
            .extracting("codigoVaga", "valor", "metodoPagamento")
            .containsExactly("A01", 15.20, EMetodoPagamento.CARTAO);
    }
}
