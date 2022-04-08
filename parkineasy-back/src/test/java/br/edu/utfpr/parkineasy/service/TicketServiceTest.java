package br.edu.utfpr.parkineasy.service;

import br.edu.utfpr.parkineasy.dto.request.TicketRequest;
import br.edu.utfpr.parkineasy.dto.response.TicketResponse;
import br.edu.utfpr.parkineasy.model.Ticket;
import br.edu.utfpr.parkineasy.repository.TicketRepository;
import br.edu.utfpr.parkineasy.service.impl.TicketServiceImpl;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;

@ExtendWith(MockitoExtension.class)
class TicketServiceTest {
    @Mock
    private TicketRepository ticketRepository;

    @Mock
    private VagaService vagaService;

    private TicketService ticketService;

    @BeforeEach
    void setUp() {
        ticketService = new TicketServiceImpl(vagaService, ticketRepository);
    }

    @Test
    void criarTicket_deveRetornarTicketResponse_quandoTicketForCriado() {
        long id = 1L;
        String codigoVaga = "A01";
        LocalDateTime dataHora = LocalDateTime.now();
        TicketRequest ticketRequest = new TicketRequest(codigoVaga);
        Ticket ticket = new Ticket();
        ticket.setId(id);
        ticket.setCodigoVaga(codigoVaga);
        ticket.setDataHora(dataHora);
        given(ticketRepository.save(any()))
            .willReturn(ticket);
        doNothing().when(vagaService).ocuparVaga(codigoVaga);
        TicketResponse result = ticketService.criarTicket(ticketRequest);
        assertThat(result)
            .extracting("id", "codigoVaga", "dataHora")
            .containsExactly(id, codigoVaga, dataHora.toString());
    }
}
