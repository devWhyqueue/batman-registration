package de.devwhyqueue.batmanregistration.repository;

import de.devwhyqueue.batmanregistration.model.DisciplineType;
import de.devwhyqueue.batmanregistration.model.Registration;
import de.devwhyqueue.batmanregistration.model.Tournament;
import de.devwhyqueue.batmanregistration.model.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegistrationRepository extends JpaRepository<Registration, Long> {

  List<Registration> findByTournamentDiscipline_Tournament(
      Tournament tournament);

  Optional<Registration> findOneByTournamentDiscipline_TournamentAndTournamentDiscipline_Discipline_DisciplineTypeAndUser(
      Tournament tournament, DisciplineType disciplineType, User user);

  default Optional<Registration> findOneByTournamentAndDisciplineTypeAndUser(
      Tournament tournament, DisciplineType disciplineType, User user) {
    return findOneByTournamentDiscipline_TournamentAndTournamentDiscipline_Discipline_DisciplineTypeAndUser(
        tournament, disciplineType,
        user);
  }
}
