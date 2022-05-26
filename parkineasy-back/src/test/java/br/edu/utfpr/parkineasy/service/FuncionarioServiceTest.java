package br.edu.utfpr.parkineasy.service;

import br.edu.utfpr.parkineasy.dto.request.FuncionarioRequest;
import br.edu.utfpr.parkineasy.dto.response.FuncionarioResponse;
import br.edu.utfpr.parkineasy.model.Caixa;
import br.edu.utfpr.parkineasy.model.Funcionario;
import br.edu.utfpr.parkineasy.model.enumeration.TipoVaga;
import br.edu.utfpr.parkineasy.repository.CaixaRepository;
import br.edu.utfpr.parkineasy.repository.FuncionarioRepository;
import br.edu.utfpr.parkineasy.service.impl.FuncionarioServiceImpl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import javax.validation.ValidationException;

import org.apache.tomcat.jni.Local;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.tuple;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FuncionarioServiceTest {
    @Mock
    private FuncionarioRepository funcionarioRepository;

    private FuncionarioService funcionarioService;
    
    @Mock
    private CaixaRepository caixaRepository;

    private static Funcionario getFuncionarioOf(Long id, String nome, String email, String senha, String usuario) {
        Funcionario funcionario = new Funcionario();
        funcionario.setId(id);
        funcionario.setNome(nome);
        funcionario.setEmail(email);
        funcionario.setSenha(senha);
        funcionario.setUsuario(usuario);
        return funcionario;
    }

    @BeforeEach
    void setUp() {
        funcionarioService = new FuncionarioServiceImpl(funcionarioRepository, new BCryptPasswordEncoder(), caixaRepository);
    }

    @Test
    void listarTodos_deveRetonarListaDeFuncionarioResponse_quandoExistirFuncionariosCadastrados() {
        Funcionario funcionario1 = getFuncionarioOf(1L, "Test1", "test1@test.com", "123456", "test1");
        Funcionario funcionario2 = getFuncionarioOf(2L, "Test2", "test2@test.com", "123456", "test2");
        given(funcionarioRepository.findAll())
            .willReturn(List.of(funcionario1, funcionario2));
        List<FuncionarioResponse> result = funcionarioService.listarTodos();
        assertThat(result)
            .extracting("id", "nome", "email", "usuario")
            .containsExactly(
                tuple(1L, "Test1", "test1@test.com", "test1"),
                tuple(2L, "Test2", "test2@test.com", "test2")
            );
    }

    @Test
    void listarTodos_deveRetornarListaDeFuncionarioResponseVazia_quandoNaoExistirFuncionariosCadastrados() {
        given(funcionarioRepository.findAll())
            .willReturn(List.of());
        List<FuncionarioResponse> result = funcionarioService.listarTodos();
        assertThat(result).isEmpty();
    }

    @Test
    void criarFuncionario_deveLancarException_quandoEmailEOuUsuarioJaEstiveremCadastrados() {
        Funcionario funcionario = new Funcionario();
        FuncionarioRequest funcionarioRequest = new FuncionarioRequest("Test", "test@test.com", "123456", "test");
        given(funcionarioRepository.findByEmailOrUsuario(anyString(), anyString()))
            .willReturn(Optional.of(funcionario));
        assertThatThrownBy(() -> funcionarioService.criarFuncionario(funcionarioRequest))
            .isExactlyInstanceOf(ValidationException.class)
            .hasMessage("Email e/ou usuario ja cadastrado(s)");
        verify(funcionarioRepository, never()).save(any());
    }

    @Test
    void criarFuncionario_deveCriarERetornarFuncionarioResponse_quandoEmailEUsuarioNaoEstiveremCadastrados() {
        given(funcionarioRepository.findByEmailOrUsuario(anyString(), anyString()))
            .willReturn(Optional.empty());
        Long id = 1L;
        String nome = "Test";
        String email = "test@test.com";
        String senha = "123456";
        String usuario = "test";
        Funcionario funcionario = getFuncionarioOf(id, nome, email, senha, usuario);
        when(funcionarioRepository.save(any()))
            .thenReturn(funcionario);
        FuncionarioRequest funcionarioRequest = new FuncionarioRequest(nome, email, senha, usuario);
        assertThat(funcionarioService.criarFuncionario(funcionarioRequest))
            .extracting("id", "nome", "email", "usuario")
            .containsExactly(id, nome, email, usuario);
    }
    
    @Test
    void getCaixa_deveRetornarCaixaDoTipoDeVaga_seTipoDeVagaNaoExistir() {
        assertThatExceptionOfType(ValidationException.class)
            .isThrownBy(() -> funcionarioService.getCaixa(4))
            .withMessage("O tipo de vaga selecionado não existe.");
    }

    @Test
    void getCaixa_deveRetornarCaixaDoTipoDeVaga_seTipoDeVagaExistir() {
        when(caixaRepository.findAllByDataPagamentoAndTipoVaga(any(), any()))
            .thenReturn(List.of(Caixa.builder().tipoVaga(1).dataPagamento(LocalDate.of(2022, 5, 10)).valor(15.2).build()));
        assertThat(funcionarioService.getCaixa(1))
            .extracting("totalCaixa", "tipoVaga")
            .containsExactly(15.2, TipoVaga.COMUM);
    }
}
