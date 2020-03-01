package de.devwhyqueue.batmanregistration.resource;

import de.devwhyqueue.batmanregistration.model.*;
import de.devwhyqueue.batmanregistration.repository.RegistrationRepository;
import de.devwhyqueue.batmanregistration.repository.TournamentRepository;
import java.util.Optional;

import de.devwhyqueue.batmanregistration.service.PlayerService;
import org.springframework.http.ResponseEntity;
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
  private PlayerService playerService;

  public RegistrationResource(
      TournamentRepository tournamentRepository, RegistrationRepository registrationRepository, PlayerService playerService) {
    this.tournamentRepository = tournamentRepository;
    this.registrationRepository = registrationRepository;
    this.playerService = playerService;
  }

  @GetMapping("/tournament/current/registrations")
  public ResponseEntity<Registration> getRegistrationsByDiscipline(@RequestParam Long disciplineId) {
    Optional<Tournament> currentTournament = this.tournamentRepository
        .findFirstByOrderByStartDesc();

    //this.registrationRepository.findByTournamentDiscipline_TournamentAndTournamentDiscipline_Discipline_Id(t, disciplineId);

    return null;
  }

  @GetMapping("/tournament/current/registrations/self")
  public Division getDivisionsByPlayerAndDisciplineType(
      @RequestParam DisciplineType disciplineType) {
    Optional<Player> player = this.playerService.getPlayerInfoFromAuthService();
    player.ifPresentOrElse(p -> System.out.println(player), () -> System.out.println());

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
