package br.edu.utfpr.parkineasy.service.impl;

import br.edu.utfpr.parkineasy.dto.request.FuncionarioRequest;
import br.edu.utfpr.parkineasy.dto.response.FuncionarioResponse;
import br.edu.utfpr.parkineasy.model.Funcionario;
import br.edu.utfpr.parkineasy.repository.FuncionarioRepository;
import br.edu.utfpr.parkineasy.service.FuncionarioService;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.validation.ValidationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class FuncionarioServiceImpl implements FuncionarioService {
    private final FuncionarioRepository funcionarioRepository;
    private final PasswordEncoder passwordEncoder;

    public FuncionarioServiceImpl(FuncionarioRepository funcionarioRepository, PasswordEncoder passwordEncoder) {
        this.funcionarioRepository = funcionarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<FuncionarioResponse> listarTodos() {
        return funcionarioRepository.findAll()
            .stream()
            .map(FuncionarioResponse::new)
            .collect(Collectors.toList());
    }

    @Override
    public FuncionarioResponse criarFuncionario(FuncionarioRequest funcionarioRequest) {
        Optional<Funcionario> funcionarioOptional = funcionarioRepository.findByEmailOrUsuario(
            funcionarioRequest.getEmail(),
            funcionarioRequest.getUsuario()
        );
        if (funcionarioOptional.isPresent()) {
            throw new ValidationException("Email e/ou usuario ja cadastrado(s)");
        }
        Funcionario funcionario = new Funcionario(
            funcionarioRequest.getNome(),
            funcionarioRequest.getEmail(),
            codificarSenha(funcionarioRequest.getSenha()),
            funcionarioRequest.getUsuario()
        );
        return new FuncionarioResponse(funcionarioRepository.save(funcionario));
    }

    private String codificarSenha(String senha) {
        return passwordEncoder.encode(senha);
    }
}
