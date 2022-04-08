package br.edu.utfpr.parkineasy.repository;

import br.edu.utfpr.parkineasy.model.Funcionario;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class FuncionarioRepositoryTest {
    @Autowired
    private FuncionarioRepository funcionarioRepository;

    private static Funcionario getFuncionarioOf(String nome, String email, String senha, String usuario) {
        return new Funcionario(nome, email, senha, usuario);
    }

    @AfterEach
    void tearDown() {
        funcionarioRepository.deleteAll();
    }

    @Test
    void findByEmail_deveRetornarOptionalPresente_quandoEmailEstiverCadastrado() {
        String email = "test@test.com";
        Funcionario funcionario = getFuncionarioOf("Test", email, "123456", "test");
        funcionarioRepository.save(funcionario);
        Optional<Funcionario> resultado = funcionarioRepository.findByEmail(email);
        assertThat(resultado).isPresent();
    }

    @Test
    void findByEmail_deveRetornarOptionalVazio_quandoEmailNaoEstiverCadastrado() {
        String email = "test@test.com";
        Optional<Funcionario> resultado = funcionarioRepository.findByEmail(email);
        assertThat(resultado).isEmpty();
    }

    @Test
    void findByUsuario_deveRetornarOptionalPresente_quandoUsuarioEstiverCadastrado() {
        String usuario = "test";
        Funcionario funcionario = getFuncionarioOf("Test", "test@test.com", "123456", usuario);
        funcionarioRepository.save(funcionario);
        Optional<Funcionario> resultado = funcionarioRepository.findByUsuario(usuario);
        assertThat(resultado).isPresent();
    }

    @Test
    void findByUsuario_deveRetornarOptionalVazio_quandoUsuarioNaoEstiverCadastrado() {
        String usuario = "test";
        Optional<Funcionario> resultado = funcionarioRepository.findByUsuario(usuario);
        assertThat(resultado).isEmpty();
    }

    @Test
    void findByEmailOrUsuario_deveRetornarOptionalPresente_quandoEmailEstiverCadastrado() {
        String email = "test@test.com";
        Funcionario funcionario = getFuncionarioOf("Test", email, "123456", "test");
        funcionarioRepository.save(funcionario);
        Optional<Funcionario> resultado = funcionarioRepository.findByEmailOrUsuario(email, "test1");
        assertThat(resultado).isPresent();
    }

    @Test
    void findByEmailOrUsuario_deveRetornarOptionalPresente_quandoUsuarioEstiverCadastrado() {
        String usuario = "test";
        Funcionario funcionario = getFuncionarioOf("Test", "test@test.com", "123456", usuario);
        funcionarioRepository.save(funcionario);
        Optional<Funcionario> resultado = funcionarioRepository.findByEmailOrUsuario("test1@test.com", usuario);
        assertThat(resultado).isPresent();
    }

    @Test
    void findByEmailOrUsuario_deveRetornarOptionalPresente_quandoEmailEUsuarioEstiveremCadastrados() {
        String email = "test@test.com";
        String usuario = "test";
        Funcionario funcionario = getFuncionarioOf("Test", email, "123456", usuario);
        funcionarioRepository.save(funcionario);
        Optional<Funcionario> resultado = funcionarioRepository.findByEmailOrUsuario(email, usuario);
        assertThat(resultado).isPresent();
    }

    @Test
    void findByEmailOrUsuario_deveRetornarOptionalVazio_quandoEmailEUsuarioNaoEstiveremCadastrados() {
        String email = "test@test.com";
        String usuario = "test";
        Optional<Funcionario> resultado = funcionarioRepository.findByEmailOrUsuario(email, usuario);
        assertThat(resultado).isEmpty();
    }
}
