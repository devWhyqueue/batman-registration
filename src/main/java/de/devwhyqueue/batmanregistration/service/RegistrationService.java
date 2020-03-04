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
import de.devwhyqueue.batmanregistration.repository.TournamentDisciplineRepository;
import de.devwhyqueue.batmanregistration.repository.TournamentRepository;
import de.devwhyqueue.batmanregistration.resource.dto.RegistrationWithPartnerDTO;
import de.devwhyqueue.batmanregistration.service.exception.AlreadyRegisteredException;
import de.devwhyqueue.batmanregistration.service.exception.CloseOfEntriesExceededException;
import de.devwhyqueue.batmanregistration.service.exception.DifferentGenderException;
import de.devwhyqueue.batmanregistration.service.exception.SameGenderException;
import de.devwhyqueue.batmanregistration.service.exception.UnavailableAuthServiceException;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RegistrationService {

  private PlayerInfoService playerInfoService;

  private UserService userService;

  private TournamentRepository tournamentRepository;
  private PlayerRepository playerRepository;
  private TournamentDisciplineRepository tournamentDisciplineRepository;
  private RegistrationRepository registrationRepository;

  public RegistrationService(
      TournamentRepository tournamentRepository,
      PlayerInfoService playerInfoService,
      UserService userService,
      PlayerRepository playerRepository,
      RegistrationRepository registrationRepository,
      TournamentDisciplineRepository tournamentDisciplineRepository) {
    this.tournamentRepository = tournamentRepository;
    this.playerInfoService = playerInfoService;
    this.userService = userService;
    this.playerRepository = playerRepository;
    this.registrationRepository = registrationRepository;
    this.tournamentDisciplineRepository = tournamentDisciplineRepository;
  }

  @Transactional(readOnly = true)
  public List<Registration> getCurrentRegistrations()
      throws EntityNotFoundException, UnavailableAuthServiceException {
    Tournament tournament = this.tournamentRepository.getFirstByOrderByStartDesc();
    if (tournament == null) {
      throw new EntityNotFoundException();
    }

    List<Registration> registrations = this.registrationRepository
        .findByTournamentDiscipline_TournamentOrderByRegistrationDate(tournament);
    for (Registration r : registrations) {
      Player player = this.playerInfoService.getPlayerInfoByIdFromAuthService(r.getUser().getId());
      r.setPlayer(player);
    }
    return registrations;
  }

  @Transactional(readOnly = true)
  public List<Registration> getOwnCurrentRegistrations()
      throws EntityNotFoundException, UnavailableAuthServiceException {
    Tournament tournament = this.tournamentRepository.getFirstByOrderByStartDesc();
    if (tournament == null) {
      throw new EntityNotFoundException();
    }

    Player player = this.playerInfoService.getOwnPlayerInfoFromAuthService();

    List<Registration> registrations = this.registrationRepository
        .findByTournamentDiscipline_TournamentAndUser_Id(tournament, player.getId());
    registrations.forEach(r -> r.setPlayer(player));

    return registrations;
  }


  public Registration registerForSingleForCurrentTournament(Division division)
      throws CloseOfEntriesExceededException,
      UnavailableAuthServiceException, AlreadyRegisteredException, EntityNotFoundException {
    Tournament tournament = this.tournamentRepository.getFirstByOrderByStartDesc();
    if (tournament == null) {
      throw new EntityNotFoundException();
    }
    if (tournament.isCloseOfEntriesExceeded()) {
      throw new CloseOfEntriesExceededException();
    }

    Player player = this.playerInfoService.getOwnPlayerInfoFromAuthService();

    if (this.registrationRepository
        .findOneByTournamentAndDisciplineTypeAndUserId(tournament, DisciplineType.SINGLE,
            player.getId())
        .isPresent()) {
      throw new AlreadyRegisteredException();
    }

    FieldType fieldType =
        player.getGender() == Gender.MALE ? FieldType.MALE : FieldType.FEMALE;
    TournamentDiscipline tDiscipline = this.tournamentDisciplineRepository
        .getByTournamentAndDisciplineTypeAndFieldTypeAndDivision(
            tournament, DisciplineType.SINGLE, fieldType, division);
    if (tDiscipline == null) {
      throw new EntityNotFoundException();
    }

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
      UnavailableAuthServiceException, AlreadyRegisteredException, EntityNotFoundException, SameGenderException, DifferentGenderException {
    Tournament tournament = this.tournamentRepository.getFirstByOrderByStartDesc();
    if (tournament == null) {
      throw new EntityNotFoundException();
    }
    if (tournament.isCloseOfEntriesExceeded()) {
      throw new CloseOfEntriesExceededException();
    }

    Player player = this.playerInfoService.getOwnPlayerInfoFromAuthService();

    if (this.registrationRepository
        .findOneByTournamentAndDisciplineTypeAndUserId(tournament, disciplineType, player.getId())
        .isPresent()) {
      throw new AlreadyRegisteredException();
    }

    FieldType fieldType =
        player.getGender() == Gender.MALE ? FieldType.MALE : FieldType.FEMALE;
    fieldType = disciplineType == DisciplineType.MIXED ? FieldType.MIXED : fieldType;
    TournamentDiscipline tDiscipline = this.tournamentDisciplineRepository
        .getByTournamentAndDisciplineTypeAndFieldTypeAndDivision(
            tournament, disciplineType, fieldType, registrationDTO.getDivision());
    if (tDiscipline == null) {
      throw new EntityNotFoundException();
    }

    User user = this.userService.getOrCreate(player.getId());

    if (disciplineType == DisciplineType.MIXED && player.getGender() == registrationDTO.getPartner()
        .getGender()) {
      throw new SameGenderException();
    }
    if (disciplineType == DisciplineType.DOUBLE && player.getGender() != registrationDTO
        .getPartner().getGender()) {
      throw new DifferentGenderException();
    }
    Player partner = this.playerRepository.save(registrationDTO.getPartner());

    Integer numOfRegistrations = this.registrationRepository
        .countByTournamentDiscipline(tDiscipline);
    Registration registration = new Registration(user, player, partner, tDiscipline,
        numOfRegistrations);

    return this.registrationRepository.save(registration);
  }

  public void cancelOwnCurrentRegistrationByDisciplineType(DisciplineType disciplineType)
      throws EntityNotFoundException, UnavailableAuthServiceException {
    Tournament tournament = this.tournamentRepository.getFirstByOrderByStartDesc();
    if (tournament == null) {
      throw new EntityNotFoundException();
    }

    Player player = this.playerInfoService.getOwnPlayerInfoFromAuthService();

    Optional<Registration> registration = this.registrationRepository
        .findOneByTournamentAndDisciplineTypeAndUserId(tournament, disciplineType, player.getId());
    registration.ifPresent(r -> {
      r.cancel();
      this.registrationRepository.save(r);
    });
  }
}
