package de.devwhyqueue.batmanregistration.repository;

import de.devwhyqueue.batmanregistration.model.DisciplineType;
import de.devwhyqueue.batmanregistration.model.Division;
import de.devwhyqueue.batmanregistration.model.FieldType;
import de.devwhyqueue.batmanregistration.model.Tournament;
import de.devwhyqueue.batmanregistration.model.TournamentDiscipline;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TournamentDisciplineRepository extends JpaRepository<TournamentDiscipline, Long> {

  TournamentDiscipline findByTournamentAndDiscipline_DisciplineTypeAndDiscipline_FieldTypeAndDiscipline_Division(
      Tournament tournament, DisciplineType disciplineType, FieldType fieldType, Division division);
}
