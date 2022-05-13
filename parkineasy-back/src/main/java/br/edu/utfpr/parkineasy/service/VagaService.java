package br.edu.utfpr.parkineasy.service;

import br.edu.utfpr.parkineasy.dto.request.VagaRequest;
import br.edu.utfpr.parkineasy.dto.response.VagaResponse;
import java.util.List;

public interface VagaService {
    List<VagaResponse> listarTodas();

    List<String> listarPorTipoVaga(Integer id);

    VagaResponse criarVaga(VagaRequest vagaRequest);

    void ocuparVaga(String codigo);
    
    List<VagaResponse> listarTodasOrdenadas();
}
