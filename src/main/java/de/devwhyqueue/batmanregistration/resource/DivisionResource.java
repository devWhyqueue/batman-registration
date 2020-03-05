package de.devwhyqueue.batmanregistration.resource;

import de.devwhyqueue.batmanregistration.model.DisciplineType;
import de.devwhyqueue.batmanregistration.model.Division;
import de.devwhyqueue.batmanregistration.model.FieldType;
import de.devwhyqueue.batmanregistration.model.Tournament;
import de.devwhyqueue.batmanregistration.repository.DivisionRepository;
import de.devwhyqueue.batmanregistration.repository.TournamentRepository;
import java.util.List;
import javax.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DivisionResource {

  private TournamentRepository tournamentRepository;
  private DivisionRepository divisionRepository;

  public DivisionResource(
      TournamentRepository tournamentRepository, DivisionRepository divisionRepository) {
    this.tournamentRepository = tournamentRepository;
    this.divisionRepository = divisionRepository;
  }

  @GetMapping("/tournaments/current/divisions")
  public ResponseEntity<List<Division>> findByDisciplineTypeAndFieldType(
      @RequestParam DisciplineType disciplineType, @RequestParam FieldType fieldType)
  throws EntityNotFoundException{
    Tournament tournament = this.tournamentRepository.getFirstByOrderByStartDesc();
    if (tournament == null) {
      throw new EntityNotFoundException();
    }

    List<Division> divisions = this.divisionRepository
        .findByTournamentAndDisciplineTypeAndFieldType(tournament, disciplineType, fieldType);

    return ResponseEntity.ok(divisions);
  }
}
