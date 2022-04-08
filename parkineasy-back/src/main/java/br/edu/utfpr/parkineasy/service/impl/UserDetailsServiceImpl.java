package br.edu.utfpr.parkineasy.service.impl;

import br.edu.utfpr.parkineasy.model.Funcionario;
import br.edu.utfpr.parkineasy.repository.FuncionarioRepository;
import java.util.Collection;
import java.util.Collections;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final FuncionarioRepository funcionarioRepository;

    public UserDetailsServiceImpl(FuncionarioRepository funcionarioRepository) {
        this.funcionarioRepository = funcionarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Funcionario funcionario = funcionarioRepository
            .findByUsuario(s)
            .orElseThrow(() -> new UsernameNotFoundException("Funcionario nao encontrado: " + s));
        return new User(
            funcionario.getUsuario(),
            funcionario.getSenha(),
            true,
            true,
            true,
            true,
            obterAutoridades("ROLE_USER")
        );
    }

    private Collection<? extends GrantedAuthority> obterAutoridades(String autoridade) {
        return Collections.singletonList(new SimpleGrantedAuthority(autoridade));
    }
}
