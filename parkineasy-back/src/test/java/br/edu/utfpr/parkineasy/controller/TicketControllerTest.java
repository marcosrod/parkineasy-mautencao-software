package br.edu.utfpr.parkineasy.controller;

import br.edu.utfpr.parkineasy.dto.request.TicketRequest;
import br.edu.utfpr.parkineasy.dto.response.TicketResponse;
import br.edu.utfpr.parkineasy.model.Ticket;
import br.edu.utfpr.parkineasy.service.TicketService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class TicketControllerTest {
    private static final String BASE_URL = "/api/v1/tickets";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TicketService ticketService;

    @Test
    void criarTicket_deveRetornarStatus201ETicketResponse_quandoTicketForCriado() throws Exception {
        long id = 1L;
        String codigoVaga = "A01";
        TicketRequest ticketRequest = new TicketRequest(codigoVaga);
        Ticket ticket = new Ticket();
        ticket.setId(id);
        ticket.setCodigoVaga(codigoVaga);
        ticket.setDataHora(LocalDateTime.now());
        given(ticketService.criarTicket(any()))
            .willReturn(new TicketResponse(ticket));
        mockMvc.perform(post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(ticketRequest)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.size()").value(3))
            .andExpect(jsonPath("$.id").value(id))
            .andExpect(jsonPath("$.codigoVaga").value(codigoVaga))
            .andExpect(jsonPath("$.dataHora").value(ticket.getDataHora().toString()));
    }
}
