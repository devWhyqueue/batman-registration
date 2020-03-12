package de.devwhyqueue.batmanregistration.service;

import de.devwhyqueue.batmanregistration.model.DisciplineType;
import de.devwhyqueue.batmanregistration.model.FieldType;
import de.devwhyqueue.batmanregistration.model.Gender;
import de.devwhyqueue.batmanregistration.model.Manager;
import de.devwhyqueue.batmanregistration.model.Player;
import de.devwhyqueue.batmanregistration.model.Registration;
import de.devwhyqueue.batmanregistration.model.Tournament;
import de.devwhyqueue.batmanregistration.model.TournamentDiscipline;
import de.devwhyqueue.batmanregistration.repository.RegistrationRepository;
import de.devwhyqueue.batmanregistration.resource.dto.RegistrationWithPartnerDTO;
import de.devwhyqueue.batmanregistration.resource.dto.SingleRegistrationDTO;
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

  private ManagerService managerService;
  private TournamentService tournamentService;
  private TournamentDisciplineService tournamentDisciplineService;

  private PlayerService playerService;
  private RegistrationRepository registrationRepository;

  public RegistrationService(
      RemoteAuthService remoteAuthService,
      ManagerService managerService,
      TournamentService tournamentService,
      TournamentDisciplineService tournamentDisciplineService,
      PlayerService playerService,
      RegistrationRepository registrationRepository) {
    this.remoteAuthService = remoteAuthService;
    this.managerService = managerService;
    this.tournamentService = tournamentService;
    this.tournamentDisciplineService = tournamentDisciplineService;
    this.playerService = playerService;
    this.registrationRepository = registrationRepository;
  }

  @Transactional(readOnly = true)
  public List<Registration> getCurrentRegistrations()
      throws NotFoundException {
    Tournament tournament = this.tournamentService.getCurrentTournament();

    List<Registration> registrations = this.registrationRepository
        .findByTournamentDiscipline_TournamentOrderByRegistrationDate(tournament);
    return registrations;
  }

  @Transactional(readOnly = true)
  public List<Registration> getOwnCurrentRegistrations()
      throws NotFoundException, UnavailableAuthServiceException {
    Tournament tournament = this.tournamentService.getCurrentTournament();

    Player manager = this.remoteAuthService.getOwnUserInfo();

    List<Registration> registrations = this.registrationRepository
        .findByTournamentDiscipline_TournamentAndCreatedByManager_Id(tournament, manager.getId());

    return registrations;
  }


  public Registration registerForSingleForCurrentTournament(
      SingleRegistrationDTO singleRegistration)
      throws CloseOfEntriesExceededException,
      UnavailableAuthServiceException, NotFoundException, AlreadyRegisteredException {
    // Check if tournament available and close of entry not exceeded
    Tournament tournament = this.tournamentService.getCurrentTournament();
    if (tournament.isCloseOfEntriesExceeded()) {
      throw new CloseOfEntriesExceededException();
    }

    // Find tournament discipline
    FieldType fieldType =
        singleRegistration.getPlayer().getGender() == Gender.MALE ? FieldType.MALE
            : FieldType.FEMALE;
    TournamentDiscipline tDiscipline = this.tournamentDisciplineService
        .findByTournamentAndDisciplineTypeAndFieldTypeAndDivision(
            tournament, DisciplineType.SINGLE, fieldType, singleRegistration.getDivision());

    // Get info about registration submitter
    Player managerInfo = this.remoteAuthService.getOwnUserInfo();
    Manager manager = this.managerService.getOrCreate(managerInfo.getId());

    // Save player
    Player player = singleRegistration.getPlayer();
    player.setCreatedByManager(manager);
    player = this.playerService.getOrCreate(player);

    // Check if already registered
    if (this.registrationRepository
        .findOneByTournamentAndDisciplineTypeAndPlayer_Id(tournament, DisciplineType.SINGLE,
            player.getId())
        .isPresent()) {
      throw new AlreadyRegisteredException();
    }

    // Save registration
    Integer numOfRegistrations = this.registrationRepository
        .countByTournamentDiscipline(tDiscipline);
    Registration registration = new Registration(manager, player, null, tDiscipline,
        numOfRegistrations);

    return this.registrationRepository.save(registration);
  }

  public Registration registerForDoubleOrMixedForCurrentTournament(
      RegistrationWithPartnerDTO registrationDTO, DisciplineType disciplineType)
      throws CloseOfEntriesExceededException,
      UnavailableAuthServiceException, NotFoundException, SameGenderException, DifferentGenderException, AlreadyRegisteredException {
    // Check if tournament available and close of entry not exceeded
    Tournament tournament = this.tournamentService.getCurrentTournament();
    if (tournament.isCloseOfEntriesExceeded()) {
      throw new CloseOfEntriesExceededException();
    }

    // Find tournament discipline
    FieldType fieldType =
        registrationDTO.getPlayer().getGender() == Gender.MALE ? FieldType.MALE : FieldType.FEMALE;
    fieldType = disciplineType == DisciplineType.MIXED ? FieldType.MIXED : fieldType;
    TournamentDiscipline tDiscipline = this.tournamentDisciplineService
        .findByTournamentAndDisciplineTypeAndFieldTypeAndDivision(
            tournament, disciplineType, fieldType, registrationDTO.getDivision());

    // Get info about registration submitter
    Player managerInfo = this.remoteAuthService.getOwnUserInfo();
    Manager manager = this.managerService.getOrCreate(managerInfo.getId());

    // Check genders
    if (disciplineType == DisciplineType.MIXED
        && registrationDTO.getPlayer().getGender() == registrationDTO.getPartner()
        .getGender()) {
      throw new SameGenderException();
    }
    if (disciplineType == DisciplineType.DOUBLE
        && registrationDTO.getPlayer().getGender() != registrationDTO
        .getPartner().getGender()) {
      throw new DifferentGenderException();
    }

    // Save player and partner
    Player player = registrationDTO.getPlayer();
    player.setCreatedByManager(manager);
    player = this.playerService.getOrCreate(player);
    Player partner = registrationDTO.getPartner();
    partner.setCreatedByManager(manager);
    partner = this.playerService.getOrCreate(partner);

    // Check if one of them is already registered
    if (this.registrationRepository
        .findOneByTournamentAndDisciplineTypeAndPlayer_Id(tournament, disciplineType,
            player.getId())
        .isPresent() ||
        this.registrationRepository
            .findOneByTournamentAndDisciplineTypeAndPlayer_Id(tournament, disciplineType,
                partner.getId()).isPresent()) {
      throw new AlreadyRegisteredException();
    }

    // Save registration
    Integer numOfRegistrations = this.registrationRepository
        .countByTournamentDiscipline(tDiscipline);
    Registration registration = new Registration(manager, player, partner, tDiscipline,
        numOfRegistrations);

    return this.registrationRepository.save(registration);
  }

  // TODO: Implement (CancelRegistrationDTO)
  public void cancelOwnCurrentRegistrationByDisciplineType(DisciplineType disciplineType)
      throws NotFoundException, UnavailableAuthServiceException {
    Tournament tournament = this.tournamentService.getCurrentTournament();

    // That's not the player, pls fix
    Player manager = this.remoteAuthService.getOwnUserInfo();

    Optional<Registration> registration = this.registrationRepository
        .findOneByTournamentAndDisciplineTypeAndPlayer_Id(tournament, disciplineType,
            manager.getId());
    registration.ifPresent(r -> {
      r.cancel();
      this.registrationRepository.save(r);
    });
  }
}
