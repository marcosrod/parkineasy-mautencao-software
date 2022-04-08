package br.edu.utfpr.parkineasy.controller;

import br.edu.utfpr.parkineasy.dto.request.VagaRequest;
import br.edu.utfpr.parkineasy.dto.response.VagaResponse;
import br.edu.utfpr.parkineasy.model.Vaga;
import br.edu.utfpr.parkineasy.model.enumeration.TipoVaga;
import br.edu.utfpr.parkineasy.service.VagaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import javax.validation.ValidationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class VagaControllerTest {
    private static final String BASE_URL = "/api/v1/gerencia/vagas";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private VagaService vagaService;

    private static VagaResponse getVagaResponseOf(String codigo, boolean ocupada, int tipoVaga) {
        Vaga vaga = new Vaga();
        vaga.setCodigo(codigo);
        vaga.setOcupada(ocupada);
        vaga.setTipoVaga(tipoVaga);
        return new VagaResponse(vaga);
    }

    @Test
    @WithMockUser
    void listarTodas_deveRetornarStatus200EListaDeVagaResponse_quandoHouverVagasCadastradas() throws Exception {
        String codigo1 = "A01";
        boolean ocupada1 = true;
        int tipoVaga1 = 1;
        String codigo2 = "B01";
        boolean ocupada2 = false;
        int tipoVaga2 = 2;
        VagaResponse vagaResponse1 = getVagaResponseOf(codigo1, ocupada1, tipoVaga1);
        VagaResponse vagaResponse2 = getVagaResponseOf(codigo2, ocupada2, tipoVaga2);
        when(vagaService.listarTodas())
            .thenReturn(List.of(vagaResponse1, vagaResponse2));
        mockMvc.perform(get(BASE_URL))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.size()").value(2))
            .andExpect(jsonPath("$[0].codigo").value(codigo1))
            .andExpect(jsonPath("$[0].ocupada").value(ocupada1))
            .andExpect(jsonPath("$[0].descricao").value(TipoVaga.valueOf(tipoVaga1).toString()))
            .andExpect(jsonPath("$[1].codigo").value(codigo2))
            .andExpect(jsonPath("$[1].ocupada").value(ocupada2))
            .andExpect(jsonPath("$[1].descricao").value(TipoVaga.valueOf(tipoVaga2).toString()));
    }

    @Test
    @WithMockUser
    void listarTodas_deveRetornarStatus200ECorpoVazio_quandoNaoHouverVagasCadastradas() throws Exception {
        given(vagaService.listarTodas())
            .willReturn(List.of());
        mockMvc.perform(get(BASE_URL))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.size()").value(0));
    }

    @Test
    @WithMockUser
    void criarVaga_deveRetornarStatus400ECorpoVazio_quandoCodigoJaEstiverCadastrado() throws Exception {
        given(vagaService.criarVaga(any()))
            .willThrow(ValidationException.class);
        VagaRequest vagaRequest = new VagaRequest("A01", 1);
        mockMvc.perform(post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(vagaRequest)))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$").doesNotExist());
    }

    @Test
    @WithMockUser
    void criarVaga_deveRetornarStatus201EVagaResponse_quandoVagaForCriadaComSucesso() throws Exception {
        String codigo = "A01";
        boolean ocupada = false;
        int tipoVaga = 1;
        VagaResponse vagaResponse = getVagaResponseOf(codigo, ocupada, tipoVaga);
        when(vagaService.criarVaga(any()))
            .thenReturn(vagaResponse);
        VagaRequest vagaRequest = new VagaRequest(codigo, tipoVaga);
        mockMvc.perform(post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(vagaRequest)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.codigo").value(codigo))
            .andExpect(jsonPath("$.ocupada").value(ocupada))
            .andExpect(jsonPath("$.descricao").value(TipoVaga.valueOf(tipoVaga).toString()));
    }
}
