package de.devwhyqueue.batmanregistration.repository;

import de.devwhyqueue.batmanregistration.model.DisciplineType;
import de.devwhyqueue.batmanregistration.model.Registration;
import de.devwhyqueue.batmanregistration.model.Tournament;
import de.devwhyqueue.batmanregistration.model.TournamentDiscipline;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegistrationRepository extends JpaRepository<Registration, Long> {

  List<Registration> findByTournamentDiscipline_TournamentOrderByRegistrationDate(
      Tournament tournament);

  List<Registration> findByTournamentDiscipline_TournamentAndCreatedByManager_Id(
      Tournament tournament, Long userId);

  Optional<Registration> findOneByTournamentDiscipline_TournamentAndTournamentDiscipline_Discipline_DisciplineTypeAndPlayer_Id(
      Tournament tournament, DisciplineType disciplineType, Long playerId);

  default Optional<Registration> findOneByTournamentAndDisciplineTypeAndPlayer_Id(
      Tournament tournament, DisciplineType disciplineType, Long playerId) {
    return findOneByTournamentDiscipline_TournamentAndTournamentDiscipline_Discipline_DisciplineTypeAndPlayer_Id(
        tournament, disciplineType,
        playerId);
  }

  Integer countByTournamentDiscipline(TournamentDiscipline tournamentDiscipline);
}
