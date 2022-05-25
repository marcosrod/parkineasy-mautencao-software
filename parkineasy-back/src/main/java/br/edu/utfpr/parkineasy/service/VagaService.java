package br.edu.utfpr.parkineasy.service;

import br.edu.utfpr.parkineasy.dto.request.VagaRequest;
import br.edu.utfpr.parkineasy.dto.response.VagaResponse;
import java.util.List;

public interface VagaService {
    List<VagaResponse> listarTodas();

    List<String> listarPorTipoVaga(Integer id);

    VagaResponse criarVaga(VagaRequest vagaRequest);
    
    VagaResponse atualizarVaga(Integer tipo, String codigoVaga);
    
    void excluirVaga(String codigoVaga);
    
    void ocuparVaga(String codigo);
    
    List<VagaResponse> listarTodasOrdenadas();
    
    List<VagaResponse> listarTodasOcupadas(Boolean ocupada);
}
