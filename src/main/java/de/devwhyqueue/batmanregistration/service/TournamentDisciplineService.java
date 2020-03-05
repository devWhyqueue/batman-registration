package de.devwhyqueue.batmanregistration.service;

import de.devwhyqueue.batmanregistration.model.DisciplineType;
import de.devwhyqueue.batmanregistration.model.Division;
import de.devwhyqueue.batmanregistration.model.FieldType;
import de.devwhyqueue.batmanregistration.model.Tournament;
import de.devwhyqueue.batmanregistration.model.TournamentDiscipline;
import de.devwhyqueue.batmanregistration.repository.TournamentDisciplineRepository;
import de.devwhyqueue.batmanregistration.service.exception.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TournamentDisciplineService {

  private TournamentDisciplineRepository tournamentDisciplineRepository;

  public TournamentDisciplineService(
      TournamentDisciplineRepository tournamentDisciplineRepository) {
    this.tournamentDisciplineRepository = tournamentDisciplineRepository;
  }

  @Transactional(readOnly = true)
  public TournamentDiscipline findByTournamentAndDisciplineTypeAndFieldTypeAndDivision(
      Tournament tournament, DisciplineType disciplineType, FieldType fieldType, Division division
  ) throws NotFoundException {
    TournamentDiscipline tournamentDiscipline = this.tournamentDisciplineRepository
        .findByTournamentAndDiscipline_DisciplineTypeAndDiscipline_FieldTypeAndDiscipline_Division(
            tournament, disciplineType, fieldType, division
        );
    if (tournamentDiscipline == null) {
      throw new NotFoundException();
    }
    return tournamentDiscipline;
  }
}
