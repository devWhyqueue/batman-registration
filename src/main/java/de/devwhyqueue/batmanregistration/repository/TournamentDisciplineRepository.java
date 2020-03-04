package de.devwhyqueue.batmanregistration.repository;

import de.devwhyqueue.batmanregistration.model.DisciplineType;
import de.devwhyqueue.batmanregistration.model.Division;
import de.devwhyqueue.batmanregistration.model.FieldType;
import de.devwhyqueue.batmanregistration.model.Tournament;
import de.devwhyqueue.batmanregistration.model.TournamentDiscipline;
import javax.persistence.EntityNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TournamentDisciplineRepository extends JpaRepository<TournamentDiscipline, Long> {

  TournamentDiscipline getByTournamentAndDiscipline_DisciplineTypeAndDiscipline_FieldTypeAndDiscipline_Division(
      Tournament tournament, DisciplineType disciplineType, FieldType fieldType, Division division)
      throws EntityNotFoundException;

  default TournamentDiscipline getByTournamentAndDisciplineTypeAndFieldTypeAndDivision(
      Tournament tournament, DisciplineType disciplineType, FieldType fieldType, Division division)
      throws EntityNotFoundException {
    return getByTournamentAndDiscipline_DisciplineTypeAndDiscipline_FieldTypeAndDiscipline_Division(
        tournament, disciplineType, fieldType, division);
  }
}
