package br.edu.utfpr.parkineasy.service.impl;

import br.edu.utfpr.parkineasy.dto.request.VagaRequest;
import br.edu.utfpr.parkineasy.dto.response.VagaResponse;
import br.edu.utfpr.parkineasy.model.Vaga;
import br.edu.utfpr.parkineasy.repository.VagaRepository;
import br.edu.utfpr.parkineasy.service.VagaService;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.validation.ValidationException;
import org.springframework.stereotype.Service;

@Service
public class VagaServiceImpl implements VagaService {
    private final VagaRepository vagaRepository;
    
    private static final Integer QTD_MAXIMA_VAGAS = 40;
    
    private static final Integer QTD_MAX_VAGAS_POR_PREFIXO = 10;
    
    private static final List<String> PREFIXOS_PERMITIDOS_CODIGO_VAGAS = List.of("A", "B", "C", "D");

    public VagaServiceImpl(VagaRepository vagaRepository) {
        this.vagaRepository = vagaRepository;
    }

    @Override
    public List<VagaResponse> listarTodas() {
        return vagaRepository.findAll()
            .stream()
            .map(VagaResponse::new)
            .collect(Collectors.toList());
    }

    @Override
    public List<String> listarPorTipoVaga(Integer id) {
        return vagaRepository.findAllByTipoVagaAndOcupada(id, Boolean.FALSE)
            .stream()
            .map(Vaga::getCodigo)
            .collect(Collectors.toList());
    }

    @Override
    public VagaResponse criarVaga(VagaRequest vagaRequest) {
        var vaga = Vaga.builder()
            .codigo(vagaRequest.getCodigo())
            .tipoVaga(vagaRequest.getTipo())
            .ocupada(false)
            .build();
        validarVaga(vaga);
        
        return new VagaResponse(vagaRepository.save(vaga));
    }
    
    private void validarVaga(Vaga vaga) {
        var codigoVaga = vaga.getCodigo().charAt(0);
        var qtdVagasPorCodigo = vagaRepository.countByCodigoContaining(String.valueOf(codigoVaga));
        var qtdVagas = vagaRepository.count();
        if (vagaRepository.existsById(vaga.getCodigo())) {
            throw new ValidationException("Já existe uma vaga cadastrada com este código.");
        }
        if (!PREFIXOS_PERMITIDOS_CODIGO_VAGAS.contains(String.valueOf(codigoVaga))) {
            throw new ValidationException("O Prefixo especificado para o cadastro da vaga não é permitido. " 
                + "Prefixos permitidos: " + PREFIXOS_PERMITIDOS_CODIGO_VAGAS);
        }
        if (qtdVagas == QTD_MAXIMA_VAGAS) {
            throw new ValidationException("Não é possível criar mais vagas, limite de " + QTD_MAXIMA_VAGAS + " vagas atingido. " 
                + "Exclua uma vaga para criar mais.");
        }
        if (qtdVagasPorCodigo == QTD_MAX_VAGAS_POR_PREFIXO.longValue()) {
            throw new ValidationException("Não é possível criar mais vagas com o código " + codigoVaga + ", limite de " + QTD_MAX_VAGAS_POR_PREFIXO 
                + " vagas atingido. Exclua uma vaga com este código para criar mais.");
        }
    }

    @Override
    public VagaResponse atualizarVaga(Integer tipo, String codigoVaga) {
        var vaga = vagaRepository.findById(codigoVaga)
            .orElseThrow(() -> new ValidationException("A vaga especificada para alteração não existe."));

        if (Objects.equals(vaga.getTipoVaga(), tipo)) {
            throw new ValidationException("O tipo selecionado para atualização já é o que está cadastrado na vaga.");
        }
        vaga.setTipoVaga(tipo);
        vagaRepository.save(vaga);
        
        return new VagaResponse(vaga);
    }

    @Override
    public void excluirVaga(String codigoVaga) {
        vagaRepository.findById(codigoVaga)
                .orElseThrow(() -> new ValidationException("A vaga especificada não existe."));
        
        vagaRepository.deleteById(codigoVaga);
    }

    @Override
    public void ocuparVaga(String codigo) {
        Optional<Vaga> vaga = vagaRepository.findById(codigo);
        vaga.ifPresent(v -> {
            v.setOcupada(true);
            vagaRepository.save(vaga.get());
        });
    }

    @Override
    public List<VagaResponse> listarTodasOrdenadas() {
        return vagaRepository.findAllByOrderByCodigoAsc()
            .stream()
            .map(VagaResponse::new)
            .collect(Collectors.toList());
    }

    @Override
    public List<List<VagaResponse>> listarTodasOrdenadasPorPrefixo() {
        var listaA = vagaRepository.findAllByCodigoContaining("A")
            .stream()
            .map(VagaResponse::new)
            .collect(Collectors.toList());
        var listaB = vagaRepository.findAllByCodigoContaining("B")
            .stream()
            .map(VagaResponse::new)
            .collect(Collectors.toList());
        var listaC = vagaRepository.findAllByCodigoContaining("C")
            .stream()
            .map(VagaResponse::new)
            .collect(Collectors.toList());
        var listaD = vagaRepository.findAllByCodigoContaining("D")
            .stream()
            .map(VagaResponse::new)
            .collect(Collectors.toList());
        return List.of(listaA, listaB, listaC, listaD);
    }

    @Override
    public List<VagaResponse> listarTodasOcupadas(Boolean ocupada) {
        return vagaRepository.findAllByOcupada(ocupada)
            .stream()
            .map(VagaResponse::new)
            .collect(Collectors.toList());
    }
}
