package de.devwhyqueue.batmanregistration.repository;

import de.devwhyqueue.batmanregistration.model.DisciplineType;
import de.devwhyqueue.batmanregistration.model.Division;
import de.devwhyqueue.batmanregistration.model.FieldType;
import de.devwhyqueue.batmanregistration.model.Tournament;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DivisionRepository extends JpaRepository<Division, String> {

  @Query(value = "select div from Division div"
      + " join div.disciplines dis join dis.tournamentDisciplines td join td.tournament t"
      + " where dis.disciplineType = :disciplineType and dis.fieldType = :fieldType"
      + " and t = :tournament")
  List<Division> findByTournamentAndDisciplineTypeAndFieldType(
      Tournament tournament, DisciplineType disciplineType, FieldType fieldType);
}
