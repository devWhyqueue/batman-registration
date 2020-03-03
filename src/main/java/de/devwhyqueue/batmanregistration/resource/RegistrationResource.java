package de.devwhyqueue.batmanregistration.resource;

import de.devwhyqueue.batmanregistration.model.DisciplineType;
import de.devwhyqueue.batmanregistration.model.Division;
import de.devwhyqueue.batmanregistration.model.Player;
import de.devwhyqueue.batmanregistration.model.Registration;
import de.devwhyqueue.batmanregistration.model.Tournament;
import de.devwhyqueue.batmanregistration.repository.RegistrationRepository;
import de.devwhyqueue.batmanregistration.repository.TournamentRepository;
import de.devwhyqueue.batmanregistration.resource.dto.RegistrationWithPartnerDTO;
import de.devwhyqueue.batmanregistration.service.PlayerService;
import java.util.List;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class RegistrationResource {

  private TournamentRepository tournamentRepository;
  private RegistrationRepository registrationRepository;
  private PlayerService playerService;

  public RegistrationResource(
      TournamentRepository tournamentRepository, RegistrationRepository registrationRepository,
      PlayerService playerService) {
    this.tournamentRepository = tournamentRepository;
    this.registrationRepository = registrationRepository;
    this.playerService = playerService;
  }

  @GetMapping("/tournament/current/registrations")
  public ResponseEntity<List<Registration>> getCurrentRegistrations() {
    Optional<Tournament> currentTournament = getCurrentTournament();

    List<Registration> registrations = this.registrationRepository
        .findByTournamentDiscipline_Tournament(currentTournament.get());
    registrations.forEach(r -> {
      Optional<Player> player = this.playerService
          .getPlayerInfoByIdFromAuthService(r.getUser().getId());
      player.orElseThrow(
          () -> new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE,
              "Could not get user data from authentication service!"));
      r.setPlayer(player.get());
    });

    return ResponseEntity.ok(registrations);
  }

  @GetMapping("/tournament/current/registrations/self/disciplineType/{disciplineType}")
  public ResponseEntity<Registration> getOwnCurrentRegistrationByDisciplineType(
      @PathVariable DisciplineType disciplineType) {
    Optional<Tournament> currentTournament = getCurrentTournament();

    Optional<Player> player = this.playerService.getOwnPlayerInfoFromAuthService();
    player.orElseThrow(
        () -> new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE,
            "Could not get user data from authentication service!"));

    Optional<Registration> registration = this.registrationRepository
        .findOneByTournamentAndDisciplineTypeAndUser(currentTournament.get(),
            disciplineType, player.get());
    registration.ifPresentOrElse(r -> r.setPlayer(player.get()), () -> {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No registration found!");
    });

    return ResponseEntity.ok(registration.get());
  }

  // TODO: Implement
  @PostMapping("/tournament/current/registrations/self/disciplineType/SINGLE")
  public Division registerSingleForCurrentTournament(@RequestBody Division division) {
    Optional<Tournament> currentTournament = getCurrentTournament();

    // Set state, set date
    // Check if capacity is not full
    // Check close of entries
    // Check if already registered (state)

    return null;
  }

  // TODO: Implement
  @PostMapping("/tournament/current/registrations/self/disciplineType/DOUBLE")
  public Division registerDoubleForCurrentTournament(
      @RequestBody RegistrationWithPartnerDTO registrationDTO) {
    Optional<Tournament> currentTournament = getCurrentTournament();

    // Set state, set date
    // Check if capacity is not full
    // Check close of entries
    // Check if already registered

    return null;
  }

  // TODO: Implement
  @PostMapping("/tournament/current/registrations/self/disciplineType/MIXED")
  public Division registerMixedForCurrentTournament(
      @RequestBody RegistrationWithPartnerDTO registrationDTO) {
    Optional<Tournament> currentTournament = getCurrentTournament();

    // Set state, set date
    // Check if capacity is not full
    // Check close of entries
    // Check if already registered

    return null;
  }

  @DeleteMapping("/tournament/current/registrations/self/disciplineType/{disciplineType}")
  public ResponseEntity<Void> cancelOwnCurrentRegistrationByDisciplineType(
      @PathVariable DisciplineType disciplineType) {
    Optional<Tournament> currentTournament = getCurrentTournament();

    Optional<Player> player = this.playerService.getOwnPlayerInfoFromAuthService();
    player.orElseThrow(
        () -> new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE,
            "Could not get user data from authentication service!"));

    Optional<Registration> registration = this.registrationRepository
        .findOneByTournamentAndDisciplineTypeAndUser(currentTournament.get(),
            disciplineType, player.get());
    registration.ifPresent(r -> {
      r.cancel();
      this.registrationRepository.save(r);
    });

    return ResponseEntity.noContent().build();
  }

  private Optional<Tournament> getCurrentTournament() throws ResponseStatusException {
    Optional<Tournament> currentTournament = this.tournamentRepository
        .findFirstByOrderByStartDesc();
    currentTournament.orElseThrow(
        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "There is no current tournament!"));

    return currentTournament;
  }
}
