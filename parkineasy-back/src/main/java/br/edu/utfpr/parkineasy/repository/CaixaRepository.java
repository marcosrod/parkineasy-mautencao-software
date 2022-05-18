package br.edu.utfpr.parkineasy.repository;

import br.edu.utfpr.parkineasy.model.Caixa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface CaixaRepository extends JpaRepository<Caixa, Integer> {
    List<Caixa> findAllByDataPagamentoAndTipoVaga(LocalDate dataPagamento, Integer tipoVaga);
}
