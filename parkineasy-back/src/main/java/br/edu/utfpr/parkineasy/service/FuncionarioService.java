package br.edu.utfpr.parkineasy.service;

import br.edu.utfpr.parkineasy.dto.request.FuncionarioRequest;
import br.edu.utfpr.parkineasy.dto.response.FuncionarioResponse;
import java.util.List;

public interface FuncionarioService {
    List<FuncionarioResponse> listarTodos();

    FuncionarioResponse criarFuncionario(FuncionarioRequest funcionarioRequest);
}
