package br.edu.utfpr.parkineasy.repository;

import br.edu.utfpr.parkineasy.model.Vaga;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VagaRepository extends JpaRepository<Vaga, String> {
    List<Vaga> findAllByTipoVagaAndOcupada(Integer id, Boolean ocupada);
    
    List<Vaga> findAllByOrderByCodigoAsc();
    
    List<Vaga> findAllByOcupada(Boolean ocupada);
    
    Long countByCodigoContaining(String codigo);
    
    List<Vaga> findAllByCodigoContaining(String codigo);
}
