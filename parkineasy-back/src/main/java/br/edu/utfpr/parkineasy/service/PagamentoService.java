package br.edu.utfpr.parkineasy.service;

import br.edu.utfpr.parkineasy.dto.request.PagamentoRequest;
import br.edu.utfpr.parkineasy.dto.response.PagamentoResponse;

public interface PagamentoService {
    PagamentoResponse pagarTicket(PagamentoRequest pagamentoRequest);
    
    Double calcularValor(Long ticketId);
}
