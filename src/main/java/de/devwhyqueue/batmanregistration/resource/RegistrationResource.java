package de.devwhyqueue.batmanregistration.resource;

import de.devwhyqueue.batmanregistration.model.Division;
import de.devwhyqueue.batmanregistration.model.Tournament;
import de.devwhyqueue.batmanregistration.repository.RegistrationRepository;
import de.devwhyqueue.batmanregistration.repository.TournamentRepository;
import java.util.Optional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegistrationResource {

  private TournamentRepository tournamentRepository;
  private RegistrationRepository registrationRepository;

  public RegistrationResource(
      TournamentRepository tournamentRepository, RegistrationRepository registrationRepository) {
    this.tournamentRepository = tournamentRepository;
    this.registrationRepository = registrationRepository;
  }

  @GetMapping("/tournament/current/registrations")
  public Division getDivisions(@RequestParam Long disciplineId) {
    Optional<Tournament> currentTournament = this.tournamentRepository
        .findFirstByOrderByStartDesc();
    currentTournament.ifPresent(t -> System.out.println(this.registrationRepository
        .findByTournamentDiscipline_TournamentAndTournamentDiscipline_Discipline_Id(t,
            disciplineId)));
    return null;
  }
}
