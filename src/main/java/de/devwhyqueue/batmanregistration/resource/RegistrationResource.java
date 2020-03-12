package de.devwhyqueue.batmanregistration.resource;

import de.devwhyqueue.batmanregistration.model.DisciplineType;
import de.devwhyqueue.batmanregistration.model.Registration;
import de.devwhyqueue.batmanregistration.resource.dto.RegistrationWithPartnerDTO;
import de.devwhyqueue.batmanregistration.resource.dto.SingleRegistrationDTO;
import de.devwhyqueue.batmanregistration.resource.exception.ResponseStatusExceptionWithCode;
import de.devwhyqueue.batmanregistration.service.RegistrationService;
import de.devwhyqueue.batmanregistration.service.exception.AlreadyRegisteredException;
import de.devwhyqueue.batmanregistration.service.exception.CloseOfEntriesExceededException;
import de.devwhyqueue.batmanregistration.service.exception.DifferentGenderException;
import de.devwhyqueue.batmanregistration.service.exception.NotFoundException;
import de.devwhyqueue.batmanregistration.service.exception.SameGenderException;
import de.devwhyqueue.batmanregistration.service.exception.UnavailableAuthServiceException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import javax.validation.Valid;
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

  private RegistrationService registrationService;

  public RegistrationResource(
      RegistrationService registrationService) {
    this.registrationService = registrationService;
  }

  @GetMapping("/tournaments/current/registrations")
  public ResponseEntity<List<Registration>> getCurrentRegistrations() {
    try {
      List<Registration> registrations = this.registrationService.getCurrentRegistrations();
      return ResponseEntity.ok(registrations);
    } catch (NotFoundException e) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
    }
  }

  @GetMapping("/tournaments/current/registrations/self")
  public ResponseEntity<List<Registration>> getOwnCurrentRegistrations() {
    try {
      List<Registration> registrations = this.registrationService.getOwnCurrentRegistrations();
      return ResponseEntity.ok(registrations);
    } catch (UnavailableAuthServiceException e) {
      throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, e.getMessage());
    } catch (NotFoundException e) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
    }
  }

  @PostMapping(value = "/tournaments/current/registrations/self/disciplineType/SINGLE")
  public ResponseEntity<Registration> registerForSingleForCurrentTournament(
      @Valid @RequestBody SingleRegistrationDTO registrationDTO) throws URISyntaxException {

    try {
      Registration registration = this.registrationService
          .registerForSingleForCurrentTournament(registrationDTO);
      return ResponseEntity.created(
          new URI(
              "/api/tournaments/"
                  + registration.getTournamentDiscipline().getTournament().getId()
                  + "/registrations/" + registration.getId()))
          .body(registration);
    } catch (CloseOfEntriesExceededException e) {
      throw new ResponseStatusExceptionWithCode(
          HttpStatus.BAD_REQUEST, e.getMessage(), "error.closeOfEntriesExceeded");
    } catch (UnavailableAuthServiceException e) {
      throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, e.getMessage());
    } catch (NotFoundException e) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
    } catch (AlreadyRegisteredException e) {
      throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
    }
  }

  @PostMapping("/tournaments/current/registrations/self/disciplineType/DOUBLE")
  public ResponseEntity<Registration> registerForDoubleForCurrentTournament(
      @Valid @RequestBody RegistrationWithPartnerDTO registrationDTO) throws URISyntaxException {
    return registerForDoubleOrMixedForCurrentTournament(registrationDTO, DisciplineType.DOUBLE);
  }

  @PostMapping("/tournaments/current/registrations/self/disciplineType/MIXED")
  public ResponseEntity<Registration> registerForMixedForCurrentTournament(
      @Valid @RequestBody RegistrationWithPartnerDTO registrationDTO) throws URISyntaxException {
    return registerForDoubleOrMixedForCurrentTournament(registrationDTO, DisciplineType.MIXED);
  }

  @DeleteMapping("/tournaments/current/registrations/self/disciplineType/{disciplineType}")
  public ResponseEntity<Void> cancelOwnCurrentRegistrationByDisciplineType(
      @PathVariable DisciplineType disciplineType) {
    try {
      this.registrationService.cancelOwnCurrentRegistrationByDisciplineType(disciplineType);
    } catch (UnavailableAuthServiceException e) {
      throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, e.getMessage());
    } catch (NotFoundException e) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
    }
    return ResponseEntity.noContent().build();
  }

  private ResponseEntity<Registration> registerForDoubleOrMixedForCurrentTournament(
      RegistrationWithPartnerDTO registrationDTO, DisciplineType disciplineType)
      throws URISyntaxException {
    try {
      Registration registration = this.registrationService
          .registerForDoubleOrMixedForCurrentTournament(registrationDTO, disciplineType);
      return ResponseEntity.created(
          new URI(
              "/api/tournaments/"
                  + registration.getTournamentDiscipline().getTournament().getId()
                  + "/registrations/" + registration.getId()))
          .body(registration);
    } catch (CloseOfEntriesExceededException e) {
      throw new ResponseStatusExceptionWithCode(
          HttpStatus.BAD_REQUEST, e.getMessage(), "error.closeOfEntriesExceeded");
    } catch (UnavailableAuthServiceException e) {
      throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, e.getMessage());
    } catch (NotFoundException e) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
    } catch (SameGenderException e) {
      throw new ResponseStatusExceptionWithCode(
          HttpStatus.BAD_REQUEST, e.getMessage(), "error.sameGender");
    } catch (DifferentGenderException e) {
      throw new ResponseStatusExceptionWithCode(
          HttpStatus.BAD_REQUEST, e.getMessage(), "error.differentGender");
    } catch (AlreadyRegisteredException e) {
      throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
    }
  }
}
