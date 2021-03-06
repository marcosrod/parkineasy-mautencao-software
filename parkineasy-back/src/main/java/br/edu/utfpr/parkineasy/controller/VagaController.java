package br.edu.utfpr.parkineasy.controller;

import br.edu.utfpr.parkineasy.dto.request.VagaRequest;
import br.edu.utfpr.parkineasy.dto.response.VagaResponse;
import br.edu.utfpr.parkineasy.service.VagaService;
import java.util.List;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/gerencia/vagas")
public class VagaController {
    private final VagaService vagaService;

    public VagaController(VagaService vagaService) {
        this.vagaService = vagaService;
    }

    @GetMapping
    public List<VagaResponse> listarTodas() {
        return vagaService.listarTodas();
    }

    @GetMapping("tipovaga/{id}")
    public List<String> listarPorTipoVaga(@PathVariable Integer id) {
        return vagaService.listarPorTipoVaga(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public VagaResponse criarVaga(@RequestBody @Valid VagaRequest vagaRequest) {
        return vagaService.criarVaga(vagaRequest);
    }
    
    @GetMapping("ordenadas")
    public List<VagaResponse> listarTodasOrdenadas() {
        return vagaService.listarTodasOrdenadas();
    }

    @GetMapping("ordenadas-por-prefixo")
    public List<List<VagaResponse>> listarTodasOrdenadasPorPrefixo() {
        return vagaService.listarTodasOrdenadasPorPrefixo();
    }
    
    @GetMapping("ocupadas")
    public List<VagaResponse> listarTodasOcupadas(@RequestParam Boolean ocupada) {
        return vagaService.listarTodasOcupadas(ocupada);
    }
    
    @PutMapping("{codigoVaga}/alterar")
    public VagaResponse alterarVaga(@RequestParam Integer tipo, @PathVariable String codigoVaga) {
        return vagaService.atualizarVaga(tipo, codigoVaga);
    }
    
    @DeleteMapping("{codigoVaga}/excluir")
    public void excluirVaga(@PathVariable String codigoVaga) {
        vagaService.excluirVaga(codigoVaga);
    }
}
