package br.edu.utfpr.parkineasy.service.impl;

import br.edu.utfpr.parkineasy.dto.request.VagaRequest;
import br.edu.utfpr.parkineasy.dto.response.VagaResponse;
import br.edu.utfpr.parkineasy.model.Vaga;
import br.edu.utfpr.parkineasy.repository.TicketRepository;
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
    
    private final TicketRepository ticketRepository;
    
    private static final Long QTD_MAXIMA_VAGAS = 40L;
    
    private static final Long QTD_MAX_VAGAS_POR_PREFIXO = 10L;
    
    private static final List<String> PREFIXOS_PERMITIDOS_CODIGO_VAGAS = List.of("A", "B", "C", "D");

    public VagaServiceImpl(VagaRepository vagaRepository, TicketRepository ticketRepository) {
        this.vagaRepository = vagaRepository;
        this.ticketRepository = ticketRepository;
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
            throw new ValidationException("J?? existe uma vaga cadastrada com este c??digo.");
        }
        if (!PREFIXOS_PERMITIDOS_CODIGO_VAGAS.contains(String.valueOf(codigoVaga))) {
            throw new ValidationException("O Prefixo especificado para o cadastro da vaga n??o ?? permitido. " 
                + "Prefixos permitidos: " + PREFIXOS_PERMITIDOS_CODIGO_VAGAS);
        }
        if (QTD_MAXIMA_VAGAS.equals(qtdVagas)) {
            throw new ValidationException("N??o ?? poss??vel criar mais vagas, limite de " + QTD_MAXIMA_VAGAS + " vagas atingido. " 
                + "Exclua uma vaga para criar mais.");
        }
        if (qtdVagasPorCodigo == QTD_MAX_VAGAS_POR_PREFIXO.longValue()) {
            throw new ValidationException("N??o ?? poss??vel criar mais vagas com o c??digo " + codigoVaga + ", limite de " + QTD_MAX_VAGAS_POR_PREFIXO 
                + " vagas atingido. Exclua uma vaga com este c??digo para criar mais.");
        }
    }

    @Override
    public VagaResponse atualizarVaga(Integer tipo, String codigoVaga) {
        var vaga = vagaRepository.findById(codigoVaga)
            .orElseThrow(() -> new ValidationException("A vaga especificada para altera????o n??o existe."));

        if (Objects.equals(vaga.getTipoVaga(), tipo)) {
            throw new ValidationException("O tipo selecionado para atualiza????o j?? ?? o que est?? cadastrado na vaga.");
        }
        vaga.setTipoVaga(tipo);
        vagaRepository.save(vaga);
        
        return new VagaResponse(vaga);
    }

    @Override
    public void excluirVaga(String codigoVaga) {
        var vaga = vagaRepository.findById(codigoVaga)
                .orElseThrow(() -> new ValidationException("A vaga especificada n??o existe."));
        
        ticketRepository.deleteAllByVaga(vaga);
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
        var listaA = vagaRepository.findAllByCodigoContainingOrderByCodigoAsc("A")
            .stream()
            .map(VagaResponse::new)
            .collect(Collectors.toList());
        var listaB = vagaRepository.findAllByCodigoContainingOrderByCodigoAsc("B")
            .stream()
            .map(VagaResponse::new)
            .collect(Collectors.toList());
        var listaC = vagaRepository.findAllByCodigoContainingOrderByCodigoAsc("C")
            .stream()
            .map(VagaResponse::new)
            .collect(Collectors.toList());
        var listaD = vagaRepository.findAllByCodigoContainingOrderByCodigoAsc("D")
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
