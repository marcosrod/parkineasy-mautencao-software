package br.edu.utfpr.parkineasy.service;


import br.edu.utfpr.parkineasy.dto.request.VagaRequest;
import br.edu.utfpr.parkineasy.dto.response.VagaResponse;
import br.edu.utfpr.parkineasy.model.Vaga;
import br.edu.utfpr.parkineasy.model.enumeration.TipoVaga;
import br.edu.utfpr.parkineasy.repository.VagaRepository;
import br.edu.utfpr.parkineasy.service.impl.VagaServiceImpl;
import java.util.List;
import javax.validation.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.tuple;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class VagaServiceTest {
    @Mock
    private VagaRepository vagaRepository;

    private VagaService vagaService;

    private static Vaga getVagaOf(String codigo, boolean ocupada, int tipoVaga) {
        Vaga vaga = new Vaga();
        vaga.setCodigo(codigo);
        vaga.setOcupada(ocupada);
        vaga.setTipoVaga(tipoVaga);
        return vaga;
    }

    @BeforeEach
    void setUp() {
        vagaService = new VagaServiceImpl(vagaRepository);
    }

    @Test
    void listarTodas_deveRetonarListaDeVagaResponse_quandoExistirVagasCadastradas() {
        Vaga vaga1 = getVagaOf("A01", true, 1);
        Vaga vaga2 = getVagaOf("A02", false, 2);
        given(vagaRepository.findAll())
            .willReturn(List.of(vaga1, vaga2));
        List<VagaResponse> result = vagaService.listarTodas();
        assertThat(result)
            .extracting("codigo", "ocupada", "descricao")
            .containsExactly(
                tuple(vaga1.getCodigo(), vaga1.getOcupada(), TipoVaga.valueOf(vaga1.getTipoVaga()).toString()),
                tuple(vaga2.getCodigo(), vaga2.getOcupada(), TipoVaga.valueOf(vaga2.getTipoVaga()).toString())
            );
    }

    @Test
    void listarTodas_deveRetornarListaDeVagaResponseVazia_quandoNaoExistirVagasCadastradas() {
        given(vagaRepository.findAll())
            .willReturn(List.of());
        List<VagaResponse> result = vagaService.listarTodas();
        assertThat(result).isEmpty();
    }

    @Test
    void listarPorTipoVaga_deveRetornarListaVazia_quandoNaoExistirVagasCadastradas() {
        given(vagaRepository.findAllByTipoVagaAndOcupada(anyInt(), anyBoolean()))
            .willReturn(List.of());
        List<String> result = vagaService.listarPorTipoVaga(1);
        assertThat(result).isEmpty();
    }

    @Test
    void listarPorTipoVaga_deveRetonarListaString_quandoExistirVagasLivres() {
        Vaga vaga1 = getVagaOf("A01", false, 1);
        Vaga vaga2 = getVagaOf("A02", false, 1);
        given(vagaRepository.findAllByTipoVagaAndOcupada(1, false))
            .willReturn(List.of(vaga1, vaga2));
        List<String> result = vagaService.listarPorTipoVaga(1);
        assertThat(result)
            .containsExactly((vaga1.getCodigo()), (vaga2.getCodigo()))
            .hasSize(2);
    }

    @Test
    void criarVaga_deveLancarException_quandoCodigoJaEstiverCadastrado() {
        VagaRequest vagaRequest = new VagaRequest("A01", 1);
        given(vagaRepository.existsById(any()))
            .willReturn(true);
        assertThatThrownBy(() -> vagaService.criarVaga(vagaRequest))
            .isExactlyInstanceOf(ValidationException.class)
            .hasMessage("Já existe uma vaga cadastrada com este código.");
        verify(vagaRepository, never()).save(any());
    }

    @Test
    void criarVaga_deveCriarERetornarVagaResponse_quandoCodigoNaoEstiverCadastradoETipoVagaForComum() {
        given(vagaRepository.existsById(any()))
            .willReturn(false);
        String codigo = "A01";
        boolean ocupada = false;
        int tipoVaga = 1;
        Vaga vaga = getVagaOf(codigo, ocupada, tipoVaga);
        when(vagaRepository.save(any()))
            .thenReturn(vaga);
        VagaRequest vagaRequest = new VagaRequest(codigo, tipoVaga);
        assertThat(vagaService.criarVaga(vagaRequest))
            .extracting("codigo", "descricao")
            .containsExactly("A01", "COMUM");
    }

    @Test
    void criarVaga_deveCriarERetornarVagaResponse_quandoCodigoNaoEstiverCadastradoETipoVagaForDeficiente() {
        given(vagaRepository.existsById(any()))
            .willReturn(false);
        String codigo = "B01";
        boolean ocupada = true;
        int tipoVaga = 2;
        Vaga vaga = getVagaOf(codigo, ocupada, tipoVaga);
        when(vagaRepository.save(any()))
            .thenReturn(vaga);
        VagaRequest vagaRequest = new VagaRequest(codigo, tipoVaga);
        assertThat(vagaService.criarVaga(vagaRequest))
            .extracting("codigo", "descricao")
            .containsExactly(codigo, "DEFICIENTE");
    }

    @Test
    void criarVaga_deveCriarERetornarVagaResponse_quandoCodigoNaoEstiverCadastradoETipoVagaForIdoso() {
        given(vagaRepository.existsById(any()))
            .willReturn(false);
        String codigo = "C01";
        boolean ocupada = false;
        int tipoVaga = 3;
        Vaga vaga = getVagaOf(codigo, ocupada, tipoVaga);
        when(vagaRepository.save(any()))
            .thenReturn(vaga);
        VagaRequest vagaRequest = new VagaRequest(codigo, tipoVaga);
        assertThat(vagaService.criarVaga(vagaRequest))
            .extracting("codigo", "descricao")
            .containsExactly(codigo, "IDOSO");
    }

    @Test
    void listarTodasOrdenadas_deveRetornarListaDeVagaResponseVazia_quandoNaoExistirVagasCadastradas() {
        given(vagaRepository.findAllByOrderByCodigoAsc())
            .willReturn(List.of());
        List<VagaResponse> result = vagaService.listarTodasOrdenadas();
        assertThat(result).isEmpty();
    }

    @Test
    void listarTodasOrdenadas_deveRetonarListaDeVagaResponseOrdenada_quandoExistirVagasCadastradas() {
        var vaga1 = getVagaOf("A01", false, 1);
        var vaga2 = getVagaOf("A02", false, 2);
        var vaga3 = getVagaOf("B02", false, 3);
        var vaga4 = getVagaOf("C03", false, 2);
        given(vagaRepository.findAllByOrderByCodigoAsc())
            .willReturn(List.of(vaga1, vaga2, vaga3, vaga4));
        List<VagaResponse> result = vagaService.listarTodasOrdenadas();
        assertThat(result)
            .extracting("codigo", "ocupada", "descricao")
            .containsExactly(
                tuple(vaga1.getCodigo(), vaga1.getOcupada(), TipoVaga.valueOf(vaga1.getTipoVaga()).toString()),
                tuple(vaga2.getCodigo(), vaga2.getOcupada(), TipoVaga.valueOf(vaga2.getTipoVaga()).toString()),
                tuple(vaga3.getCodigo(), vaga3.getOcupada(), TipoVaga.valueOf(vaga3.getTipoVaga()).toString()),
                tuple(vaga4.getCodigo(), vaga4.getOcupada(), TipoVaga.valueOf(vaga4.getTipoVaga()).toString())
            );
    }
}
