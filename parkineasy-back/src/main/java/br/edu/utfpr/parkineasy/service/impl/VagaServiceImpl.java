package br.edu.utfpr.parkineasy.service.impl;

import br.edu.utfpr.parkineasy.dto.request.VagaRequest;
import br.edu.utfpr.parkineasy.dto.response.VagaResponse;
import br.edu.utfpr.parkineasy.model.Vaga;
import br.edu.utfpr.parkineasy.repository.VagaRepository;
import br.edu.utfpr.parkineasy.service.VagaService;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.validation.ValidationException;
import org.springframework.stereotype.Service;

@Service
public class VagaServiceImpl implements VagaService {
    private final VagaRepository vagaRepository;

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
        if (vagaRepository.existsById(vagaRequest.getCodigo())) {
            throw new ValidationException("Vaga ja cadastrada");
        }
        Vaga vaga = new Vaga(
            vagaRequest.getCodigo(),
            vagaRequest.getTipo()
        );
        return new VagaResponse(vagaRepository.save(vaga));
    }

    @Override
    public void ocuparVaga(String codigo) {
        Optional<Vaga> vaga = vagaRepository.findById(codigo);
        vaga.ifPresent(v -> {
            v.setOcupada(true);
            vagaRepository.save(vaga.get());
        });
    }
}
