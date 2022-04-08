package br.edu.utfpr.parkineasy.repository;

import br.edu.utfpr.parkineasy.model.Vaga;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

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
        String codigo = "A01";
        int tipoVaga = 1;
        Vaga vaga = new Vaga(codigo, tipoVaga);
        vagaRepository.save(vaga);
        List<Vaga> vagas = vagaRepository.findAllByTipoVagaAndOcupada(1, false);
        assertThat(vagas).filteredOn(v -> v.getCodigo().equals(codigo)).isNotEmpty().hasSize(1);
    }
}
