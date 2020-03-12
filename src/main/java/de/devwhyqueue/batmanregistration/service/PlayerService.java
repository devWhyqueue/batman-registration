package de.devwhyqueue.batmanregistration.service;

import de.devwhyqueue.batmanregistration.model.Player;
import de.devwhyqueue.batmanregistration.repository.PlayerRepository;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PlayerService {

  private PlayerRepository playerRepository;

  public PlayerService(PlayerRepository playerRepository) {
    this.playerRepository = playerRepository;
  }

  public Player getOrCreate(Player player) {
    Optional<Player> playerOpt = this.playerRepository
        .findOneByFirstNameAndLastNameAndGenderAndClub(player.getFirstName(), player.getLastName(),
            player.getGender(), player.getClub());
    if (playerOpt.isEmpty()) {
      return this.playerRepository.save(player);
    } else {
      return playerOpt.get();
    }
  }
}
