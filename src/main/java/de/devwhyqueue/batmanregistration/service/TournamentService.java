package de.devwhyqueue.batmanregistration.service;

import de.devwhyqueue.batmanregistration.model.Tournament;
import de.devwhyqueue.batmanregistration.repository.TournamentRepository;
import de.devwhyqueue.batmanregistration.service.exception.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TournamentService {

  private TournamentRepository tournamentRepository;

  public TournamentService(
      TournamentRepository tournamentRepository) {
    this.tournamentRepository = tournamentRepository;
  }

  @Transactional(readOnly = true)
  public Tournament getCurrentTournament() throws NotFoundException {
    Tournament tournament = this.tournamentRepository.findFirstByOrderByStartDesc();
    if (tournament == null) {
      throw new NotFoundException();
    }
    return tournament;
  }
}
