package de.devwhyqueue.batmanregistration.resource;

import de.devwhyqueue.batmanregistration.model.DisciplineType;
import de.devwhyqueue.batmanregistration.model.Division;
import de.devwhyqueue.batmanregistration.model.FieldType;
import de.devwhyqueue.batmanregistration.model.Tournament;
import de.devwhyqueue.batmanregistration.repository.DivisionRepository;
import de.devwhyqueue.batmanregistration.service.TournamentService;
import de.devwhyqueue.batmanregistration.service.exception.NotFoundException;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class DivisionResource {

  private TournamentService tournamentService;
  private DivisionRepository divisionRepository;

  public DivisionResource(
      TournamentService tournamentService,
      DivisionRepository divisionRepository) {
    this.tournamentService = tournamentService;
    this.divisionRepository = divisionRepository;
  }

  @GetMapping("/tournaments/current/divisions")
  public ResponseEntity<List<Division>> findByDisciplineTypeAndFieldType(
      @RequestParam DisciplineType disciplineType, @RequestParam FieldType fieldType) {
    try {
      Tournament tournament = this.tournamentService.getCurrentTournament();
      List<Division> divisions = this.divisionRepository
          .findByTournamentAndDisciplineTypeAndFieldType(tournament, disciplineType, fieldType);
      return ResponseEntity.ok(divisions);
    } catch (NotFoundException e) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
    }
  }
}
