package br.edu.utfpr.parkineasy.service.impl;

import br.edu.utfpr.parkineasy.dto.response.CaixaResponse;
import br.edu.utfpr.parkineasy.dto.request.FuncionarioRequest;
import br.edu.utfpr.parkineasy.dto.response.FuncionarioResponse;
import br.edu.utfpr.parkineasy.model.Caixa;
import br.edu.utfpr.parkineasy.model.Funcionario;
import br.edu.utfpr.parkineasy.model.enumeration.TipoVaga;
import br.edu.utfpr.parkineasy.repository.CaixaRepository;
import br.edu.utfpr.parkineasy.repository.FuncionarioRepository;
import br.edu.utfpr.parkineasy.service.FuncionarioService;

import java.time.LocalDate;
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
    
    private final CaixaRepository caixaRepository;

    public FuncionarioServiceImpl(FuncionarioRepository funcionarioRepository, PasswordEncoder passwordEncoder, CaixaRepository caixaRepository) {
        this.funcionarioRepository = funcionarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.caixaRepository = caixaRepository;
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

    @Override
    public CaixaResponse getCaixa(Integer tipoVaga) {
        if (tipoVaga < 1 || tipoVaga > 3) {
            throw new ValidationException("O tipo de vaga selecionado não existe.");
        }
        var valorTotal = caixaRepository.findAllByDataPagamentoAndTipoVaga(LocalDate.now(), tipoVaga).stream()
            .map(Caixa::getValor)
            .mapToDouble(Double::doubleValue)
                .sum();
        
        return CaixaResponse.builder()
            .dataReferencia(LocalDate.now())
            .totalCaixa(valorTotal)
            .tipoVaga(TipoVaga.valueOf(tipoVaga)).build();
    }

    private String codificarSenha(String senha) {
        return passwordEncoder.encode(senha);
    }
}
