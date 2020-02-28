package de.devwhyqueue.batmanregistration.repository;

import de.devwhyqueue.batmanregistration.model.Tournament;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TournamentRepository extends JpaRepository<Tournament, Long> {

  Optional<Tournament> findFirstByOrderByStartDesc();
}
