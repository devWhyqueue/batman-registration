package de.devwhyqueue.batmanregistration.service;

import de.devwhyqueue.batmanregistration.model.DisciplineType;
import de.devwhyqueue.batmanregistration.model.Division;
import de.devwhyqueue.batmanregistration.model.FieldType;
import de.devwhyqueue.batmanregistration.model.Gender;
import de.devwhyqueue.batmanregistration.model.Player;
import de.devwhyqueue.batmanregistration.model.Registration;
import de.devwhyqueue.batmanregistration.model.Tournament;
import de.devwhyqueue.batmanregistration.model.TournamentDiscipline;
import de.devwhyqueue.batmanregistration.model.User;
import de.devwhyqueue.batmanregistration.repository.PlayerRepository;
import de.devwhyqueue.batmanregistration.repository.RegistrationRepository;
import de.devwhyqueue.batmanregistration.resource.dto.RegistrationWithPartnerDTO;
import de.devwhyqueue.batmanregistration.service.exception.AlreadyRegisteredException;
import de.devwhyqueue.batmanregistration.service.exception.CloseOfEntriesExceededException;
import de.devwhyqueue.batmanregistration.service.exception.DifferentGenderException;
import de.devwhyqueue.batmanregistration.service.exception.NotFoundException;
import de.devwhyqueue.batmanregistration.service.exception.SameGenderException;
import de.devwhyqueue.batmanregistration.service.exception.UnavailableAuthServiceException;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RegistrationService {

  private RemoteAuthService remoteAuthService;

  private UserService userService;
  private TournamentService tournamentService;
  private TournamentDisciplineService tournamentDisciplineService;

  private PlayerRepository playerRepository;
  private RegistrationRepository registrationRepository;

  public RegistrationService(
      RemoteAuthService remoteAuthService,
      UserService userService,
      TournamentService tournamentService,
      TournamentDisciplineService tournamentDisciplineService,
      PlayerRepository playerRepository,
      RegistrationRepository registrationRepository) {
    this.remoteAuthService = remoteAuthService;
    this.userService = userService;
    this.tournamentService = tournamentService;
    this.tournamentDisciplineService = tournamentDisciplineService;
    this.playerRepository = playerRepository;
    this.registrationRepository = registrationRepository;
  }

  @Transactional(readOnly = true)
  public List<Registration> getCurrentRegistrations()
      throws NotFoundException, UnavailableAuthServiceException {
    Tournament tournament = this.tournamentService.getCurrentTournament();

    List<Registration> registrations = this.registrationRepository
        .findByTournamentDiscipline_TournamentOrderByRegistrationDate(tournament);
    for (Registration r : registrations) {
      Player player = this.remoteAuthService.getPlayerInfoById(r.getUser().getId());
      r.setPlayer(player);
    }
    return registrations;
  }

  @Transactional(readOnly = true)
  public List<Registration> getOwnCurrentRegistrations()
      throws NotFoundException, UnavailableAuthServiceException {
    Tournament tournament = this.tournamentService.getCurrentTournament();

    Player player = this.remoteAuthService.getOwnPlayerInfo();

    List<Registration> registrations = this.registrationRepository
        .findByTournamentDiscipline_TournamentAndUser_Id(tournament, player.getId());
    registrations.forEach(r -> r.setPlayer(player));

    return registrations;
  }


  public Registration registerForSingleForCurrentTournament(Division division)
      throws CloseOfEntriesExceededException,
      UnavailableAuthServiceException, AlreadyRegisteredException, NotFoundException {
    Tournament tournament = this.tournamentService.getCurrentTournament();
    if (tournament.isCloseOfEntriesExceeded()) {
      throw new CloseOfEntriesExceededException();
    }

    Player player = this.remoteAuthService.getOwnPlayerInfo();

    if (this.registrationRepository
        .findOneByTournamentAndDisciplineTypeAndUserId(tournament, DisciplineType.SINGLE,
            player.getId())
        .isPresent()) {
      throw new AlreadyRegisteredException();
    }

    FieldType fieldType =
        player.getGender() == Gender.MALE ? FieldType.MALE : FieldType.FEMALE;
    TournamentDiscipline tDiscipline = this.tournamentDisciplineService
        .findByTournamentAndDisciplineTypeAndFieldTypeAndDivision(
            tournament, DisciplineType.SINGLE, fieldType, division);

    User user = this.userService.getOrCreate(player.getId());

    Integer numOfRegistrations = this.registrationRepository
        .countByTournamentDiscipline(tDiscipline);
    Registration registration = new Registration(user, player, null, tDiscipline,
        numOfRegistrations);

    return this.registrationRepository.save(registration);
  }

  public Registration registerForDoubleOrMixedForCurrentTournament(
      RegistrationWithPartnerDTO registrationDTO, DisciplineType disciplineType)
      throws CloseOfEntriesExceededException,
      UnavailableAuthServiceException, AlreadyRegisteredException, NotFoundException, SameGenderException, DifferentGenderException {
    Tournament tournament = this.tournamentService.getCurrentTournament();
    if (tournament.isCloseOfEntriesExceeded()) {
      throw new CloseOfEntriesExceededException();
    }

    Player player = this.remoteAuthService.getOwnPlayerInfo();

    if (this.registrationRepository
        .findOneByTournamentAndDisciplineTypeAndUserId(tournament, disciplineType, player.getId())
        .isPresent()) {
      throw new AlreadyRegisteredException();
    }

    FieldType fieldType =
        player.getGender() == Gender.MALE ? FieldType.MALE : FieldType.FEMALE;
    fieldType = disciplineType == DisciplineType.MIXED ? FieldType.MIXED : fieldType;
    TournamentDiscipline tDiscipline = this.tournamentDisciplineService
        .findByTournamentAndDisciplineTypeAndFieldTypeAndDivision(
            tournament, disciplineType, fieldType, registrationDTO.getDivision());

    User user = this.userService.getOrCreate(player.getId());

    if (disciplineType == DisciplineType.MIXED && player.getGender() == registrationDTO.getPartner()
        .getGender()) {
      throw new SameGenderException();
    }
    if (disciplineType == DisciplineType.DOUBLE && player.getGender() != registrationDTO
        .getPartner().getGender()) {
      throw new DifferentGenderException();
    }
    Player partner = registrationDTO.getPartner();
    partner.setCreatedByUser(user);
    partner = this.playerRepository.save(partner);

    Integer numOfRegistrations = this.registrationRepository
        .countByTournamentDiscipline(tDiscipline);
    Registration registration = new Registration(user, player, partner, tDiscipline,
        numOfRegistrations);

    return this.registrationRepository.save(registration);
  }

  public void cancelOwnCurrentRegistrationByDisciplineType(DisciplineType disciplineType)
      throws NotFoundException, UnavailableAuthServiceException {
    Tournament tournament = this.tournamentService.getCurrentTournament();

    Player player = this.remoteAuthService.getOwnPlayerInfo();

    Optional<Registration> registration = this.registrationRepository
        .findOneByTournamentAndDisciplineTypeAndUserId(tournament, disciplineType, player.getId());
    registration.ifPresent(r -> {
      r.cancel();
      this.registrationRepository.save(r);
    });
  }
}
