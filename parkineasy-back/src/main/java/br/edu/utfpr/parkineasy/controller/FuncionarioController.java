package br.edu.utfpr.parkineasy.controller;

import br.edu.utfpr.parkineasy.dto.CaixaResponse;
import br.edu.utfpr.parkineasy.dto.request.FuncionarioRequest;
import br.edu.utfpr.parkineasy.dto.response.FuncionarioResponse;
import br.edu.utfpr.parkineasy.service.FuncionarioService;
import java.util.List;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/gerencia/funcionarios")
public class FuncionarioController {
    private final FuncionarioService funcionarioService;

    public FuncionarioController(FuncionarioService funcionarioService) {
        this.funcionarioService = funcionarioService;
    }

    @GetMapping
    public List<FuncionarioResponse> listarTodos() {
        return funcionarioService.listarTodos();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FuncionarioResponse criarFuncionario(@RequestBody @Valid FuncionarioRequest funcionarioRequest) {
        return funcionarioService.criarFuncionario(funcionarioRequest);
    }
    
    @GetMapping("{tipoVaga}/caixa")
    public CaixaResponse getCaixa(@PathVariable Integer tipoVaga) {
        return funcionarioService.getCaixa(tipoVaga);
    }
}
