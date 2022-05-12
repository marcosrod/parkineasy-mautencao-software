package br.edu.utfpr.parkineasy.repository;

import br.edu.utfpr.parkineasy.model.Pagamento;
import br.edu.utfpr.parkineasy.model.Ticket;
import br.edu.utfpr.parkineasy.model.Vaga;
import br.edu.utfpr.parkineasy.model.enumeration.EMetodoPagamento;
import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;

@DataJpaTest
public class PagamentoRepositoryTest {
    
    @Autowired
    PagamentoRepository pagamentoRepository;
    
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

    @AfterEach
    void tearDown() {
        pagamentoRepository.deleteAll();
    }
}
