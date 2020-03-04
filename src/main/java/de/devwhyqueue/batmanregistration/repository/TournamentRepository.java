package de.devwhyqueue.batmanregistration.repository;

import de.devwhyqueue.batmanregistration.model.Tournament;
import javax.persistence.EntityNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TournamentRepository extends JpaRepository<Tournament, Long> {

  Tournament getFirstByOrderByStartDesc() throws EntityNotFoundException;
}
