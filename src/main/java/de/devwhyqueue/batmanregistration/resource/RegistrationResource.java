package de.devwhyqueue.batmanregistration.resource;

import de.devwhyqueue.batmanregistration.model.DisciplineType;
import de.devwhyqueue.batmanregistration.model.Division;
import de.devwhyqueue.batmanregistration.model.Registration;
import de.devwhyqueue.batmanregistration.model.Tournament;
import de.devwhyqueue.batmanregistration.repository.RegistrationRepository;
import de.devwhyqueue.batmanregistration.repository.TournamentRepository;
import java.util.Optional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
  public Division getRegistrationsByDiscipline(@RequestParam Long disciplineId) {
    Optional<Tournament> currentTournament = this.tournamentRepository
        .findFirstByOrderByStartDesc();

    currentTournament.ifPresent(t -> System.out.println(this.registrationRepository
        .findByTournamentDiscipline_TournamentAndTournamentDiscipline_Discipline_Id(t,
            disciplineId)));
    return null;
  }

  @GetMapping("/tournament/current/registrations/self")
  public Division getDivisionsByPlayerAndDisciplineType(
      @RequestParam DisciplineType disciplineType) {
    Optional<Tournament> currentTournament = this.tournamentRepository
        .findFirstByOrderByStartDesc();
    currentTournament.ifPresent(t -> System.out.println(this.registrationRepository
        .findByTournamentDiscipline_TournamentAndTournamentDiscipline_Discipline_DisciplineTypeAndPlayer_Id(
            t,
            disciplineType, 1l)));
    return null;
  }

  @PostMapping("/tournament/current/registrations")
  public Division register(@RequestBody Registration registration) {
    Optional<Tournament> currentTournament = this.tournamentRepository
        .findFirstByOrderByStartDesc();
    currentTournament.ifPresent(t -> System.out.println(this.registrationRepository
        .save(registration)));
    return null;
  }

  @DeleteMapping("/tournament/current/registrations/self/disciplineType/{disciplineType}")
  public Division deleteRegistration(@PathVariable DisciplineType disciplineType) {
    Optional<Tournament> currentTournament = this.tournamentRepository
        .findFirstByOrderByStartDesc();
    currentTournament.ifPresent(t -> this.registrationRepository.deleteById(1l));
    return null;
  }
}
