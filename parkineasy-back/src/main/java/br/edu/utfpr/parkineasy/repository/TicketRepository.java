package br.edu.utfpr.parkineasy.repository;

import br.edu.utfpr.parkineasy.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
}
