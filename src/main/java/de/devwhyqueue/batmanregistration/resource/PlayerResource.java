package de.devwhyqueue.batmanregistration.resource;

import de.devwhyqueue.batmanregistration.model.Player;
import de.devwhyqueue.batmanregistration.repository.ManagerRepository;
import de.devwhyqueue.batmanregistration.service.RemoteAuthService;
import de.devwhyqueue.batmanregistration.service.exception.UnavailableAuthServiceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class PlayerResource {

  private RemoteAuthService remoteAuthService;
  private ManagerRepository managerRepository;

  public PlayerResource(
      RemoteAuthService remoteAuthService,
      ManagerRepository managerRepository) {
    this.remoteAuthService = remoteAuthService;
    this.managerRepository = managerRepository;
  }

  @DeleteMapping("/players/self")
  public ResponseEntity<Void> deletePlayer() {
    try {
      Player player = this.remoteAuthService.getOwnUserInfo();

      this.remoteAuthService.deleteOwnUser();
      if (this.managerRepository.existsById(player.getId())) {
        this.managerRepository.deleteById(player.getId());
      }
      return ResponseEntity.noContent().build();
    } catch (UnavailableAuthServiceException e) {
      throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, e.getMessage());
    }
  }

}
