package de.devwhyqueue.batmanregistration.repository;

import de.devwhyqueue.batmanregistration.model.DisciplineType;
import de.devwhyqueue.batmanregistration.model.Registration;
import de.devwhyqueue.batmanregistration.model.Tournament;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegistrationRepository extends JpaRepository<Registration, Long> {

  List<Registration> findByTournamentDiscipline_TournamentAndTournamentDiscipline_Discipline_Id(
      Tournament tournament, Long disciplineId);

  List<Registration> findByTournamentDiscipline_TournamentAndTournamentDiscipline_Discipline_DisciplineTypeAndPlayer_Id(
      Tournament tournament, DisciplineType disciplineType, Long playerId);
}
