package br.edu.utfpr.parkineasy.repository;

import br.edu.utfpr.parkineasy.model.Vaga;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

@DataJpaTest
class VagaRepositoryTest {
    @Autowired
    private VagaRepository vagaRepository;

    @AfterEach
    void tearDown() {
        vagaRepository.deleteAll();
    }

    @Test
    void findAllByTipoVagaAndOcupada_deveRetornarListaVazia_quandoNaoHouverVagaDoTipo() {
        List<Vaga> vagas = vagaRepository.findAllByTipoVagaAndOcupada(1, false);
        assertThat(vagas).isEqualTo(List.of());
    }

    @Test
    void findAllByTipoVagaAndOcupada_deveRetornarListaString_quandoHouverVagaDoTipo() {
        var codigo = "A01";
        saveVagas(List.of("A01"));
        List<Vaga> vagas = vagaRepository.findAllByTipoVagaAndOcupada(1, false);
        assertThat(vagas).filteredOn(v -> v.getCodigo().equals(codigo)).isNotEmpty().hasSize(1);
    }
    
    @Test
    void findAllByOrderByCodigoAsc_deveRetornarVagasOrdenadasAsc_seHouveremVagas() {
        saveVagas(List.of("A01", "A02", "A03", "B01", "B05", "C02", "D01", "F03"));
        assertThat(vagaRepository.findAllByOrderByCodigoAsc())
            .extracting("codigo", "ocupada", "tipoVaga")
            .containsExactly(tuple("A01", false, 1),
                tuple("A02", false, 1),
                tuple("A03", false, 1),
                tuple("B01", false, 1),
                tuple("B05", false, 1),
                tuple("C02", false, 1),
                tuple("D01", false, 1),
                tuple("F03", false, 1));
    }
    
    public List<Vaga> saveVagas(List<String> codigos) {
        return codigos.stream()
            .map(codigo -> {
                return vagaRepository.save(new Vaga(codigo, 1));
            }).collect(Collectors.toList());
    }
}
