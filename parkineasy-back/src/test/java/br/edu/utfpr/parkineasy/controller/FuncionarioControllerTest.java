package br.edu.utfpr.parkineasy.controller;

import br.edu.utfpr.parkineasy.dto.request.FuncionarioRequest;
import br.edu.utfpr.parkineasy.dto.response.FuncionarioResponse;
import br.edu.utfpr.parkineasy.model.Funcionario;
import br.edu.utfpr.parkineasy.service.FuncionarioService;
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
class FuncionarioControllerTest {
    private static final String BASE_URL = "/api/v1/gerencia/funcionarios";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private FuncionarioService funcionarioService;

    private static FuncionarioResponse getFuncionarioResponseOf(Long id, String nome, String email, String usuario) {
        Funcionario funcionario = new Funcionario();
        funcionario.setId(id);
        funcionario.setNome(nome);
        funcionario.setEmail(email);
        funcionario.setSenha("123456");
        funcionario.setUsuario(usuario);
        return new FuncionarioResponse(funcionario);
    }

    @Test
    @WithMockUser
    void listarTodos_deveRetornarStatus200EListaDeFuncionarioResponse_quandoHouverFuncionariosCadastrados() throws Exception {
        Long id1 = 1L;
        String nome1 = "Test1";
        String email1 = "test1@test.com";
        String usuario1 = "test1";
        Long id2 = 2L;
        String nome2 = "Test2";
        String email2 = "test2@test.com";
        String usuario2 = "test2";
        FuncionarioResponse funcionarioResponse1 = getFuncionarioResponseOf(id1, nome1, email1, usuario1);
        FuncionarioResponse funcionarioResponse2 = getFuncionarioResponseOf(id2, nome2, email2, usuario2);
        when(funcionarioService.listarTodos())
            .thenReturn(List.of(funcionarioResponse1, funcionarioResponse2));
        mockMvc.perform(get(BASE_URL))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.size()").value(2))
            .andExpect(jsonPath("$[0].id").value(id1))
            .andExpect(jsonPath("$[0].nome").value(nome1))
            .andExpect(jsonPath("$[0].email").value(email1))
            .andExpect(jsonPath("$[0].usuario").value(usuario1))
            .andExpect(jsonPath("$[1].id").value(id2))
            .andExpect(jsonPath("$[1].nome").value(nome2))
            .andExpect(jsonPath("$[1].email").value(email2))
            .andExpect(jsonPath("$[1].usuario").value(usuario2));
    }

    @Test
    @WithMockUser
    void listarTodos_deveRetornarStatus200ECorpoVazio_quandoNaoHouverFuncionariosCadastrados() throws Exception {
        given(funcionarioService.listarTodos())
            .willReturn(List.of());
        mockMvc.perform(get(BASE_URL))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.size()").value(0));
    }

    @Test
    @WithMockUser
    void criarFuncionario_deveRetornarStatus400ECorpoVazio_quandoEmailEOuUsuarioJaEstiveremCadastrados() throws Exception {
        given(funcionarioService.criarFuncionario(any()))
            .willThrow(ValidationException.class);
        FuncionarioRequest funcionarioRequest = new FuncionarioRequest("Test", "test@test.com", "123456", "test");
        mockMvc.perform(post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(funcionarioRequest)))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$").doesNotExist());
    }

    @Test
    @WithMockUser
    void criarFuncionario_deveRetornarStatus201EFuncionarioResponse_quandoFuncionarioForCriadoComSucesso() throws Exception {
        Long id = 1L;
        String nome = "Test";
        String email = "test@test.com";
        String senha = "123456";
        String usuario = "test";
        FuncionarioResponse funcionarioResponse = getFuncionarioResponseOf(id, nome, email, usuario);
        when(funcionarioService.criarFuncionario(any()))
            .thenReturn(funcionarioResponse);
        FuncionarioRequest funcionarioRequest = new FuncionarioRequest(nome, email, senha, usuario);
        mockMvc.perform(post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(funcionarioRequest)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").value(id))
            .andExpect(jsonPath("$.nome").value(nome))
            .andExpect(jsonPath("$.email").value(email))
            .andExpect(jsonPath("$.usuario").value(usuario));
    }
}
