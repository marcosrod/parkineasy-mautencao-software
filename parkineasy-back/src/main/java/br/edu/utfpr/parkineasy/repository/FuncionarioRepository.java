package br.edu.utfpr.parkineasy.repository;

import br.edu.utfpr.parkineasy.model.Funcionario;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {
    Optional<Funcionario> findByEmail(String email);

    Optional<Funcionario> findByUsuario(String usuario);

    Optional<Funcionario> findByEmailOrUsuario(String email, String usuario);
}
