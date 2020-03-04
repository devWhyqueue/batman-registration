package de.devwhyqueue.batmanregistration.repository;

import de.devwhyqueue.batmanregistration.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {

}
