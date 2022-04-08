package br.edu.utfpr.parkineasy.controller;

import br.edu.utfpr.parkineasy.dto.request.LoginRequest;
import br.edu.utfpr.parkineasy.dto.response.LoginResponse;
import br.edu.utfpr.parkineasy.service.AuthService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerTest {
    private static final String BASE_URL = "/auth";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AuthService authService;

    @Test
    void login_deveRetornarStatus200ELoginResponse_quandoUsuarioESenhaForemValidos() throws Exception {
        String usuario = "test";
        String senha = "123456";
        String authenticationToken = "authenticationToken";
        LoginResponse loginResponse = new LoginResponse(authenticationToken, usuario);
        when(authService.login(any()))
            .thenReturn(loginResponse);
        LoginRequest loginRequest = new LoginRequest(usuario, senha);
        mockMvc.perform(post(BASE_URL + "/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.usuario").value(usuario))
            .andExpect(jsonPath("$.authenticationToken").value(authenticationToken));
    }
}
