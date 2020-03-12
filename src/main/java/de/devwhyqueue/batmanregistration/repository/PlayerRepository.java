package de.devwhyqueue.batmanregistration.repository;

import de.devwhyqueue.batmanregistration.model.Gender;
import de.devwhyqueue.batmanregistration.model.Player;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {

  Optional<Player> findOneByFirstNameAndLastNameAndGenderAndClub(String firstName, String lastName,
      Gender gender, String club);

}
