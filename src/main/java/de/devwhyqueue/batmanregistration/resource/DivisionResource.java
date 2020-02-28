package de.devwhyqueue.batmanregistration.resource;

import de.devwhyqueue.batmanregistration.model.DisciplineType;
import de.devwhyqueue.batmanregistration.model.Division;
import de.devwhyqueue.batmanregistration.model.FieldType;
import de.devwhyqueue.batmanregistration.model.Tournament;
import de.devwhyqueue.batmanregistration.repository.DivisionRepository;
import de.devwhyqueue.batmanregistration.repository.TournamentRepository;
import java.util.Optional;
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

  @GetMapping("/tournament/current/divisions")
  public Division getDivisions(@RequestParam DisciplineType disciplineType,
      @RequestParam FieldType fieldType) {
    Optional<Tournament> currentTournament = this.tournamentRepository
        .findFirstByOrderByStartDesc();
    currentTournament.ifPresent(t -> System.out.println(this.divisionRepository
        .findByTournamentAndDisciplineTypeAndFieldType(t, disciplineType, fieldType)));
    return null;
  }
}
