package de.devwhyqueue.batmanregistration.resource;

import de.devwhyqueue.batmanregistration.repository.UserRepository;
import de.devwhyqueue.batmanregistration.service.RemoteAuthService;
import de.devwhyqueue.batmanregistration.service.exception.UnavailableAuthServiceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class PlayerResource {

  private RemoteAuthService remoteAuthService;
  private UserRepository userRepository;

  public PlayerResource(
      RemoteAuthService remoteAuthService,
      UserRepository userRepository) {
    this.remoteAuthService = remoteAuthService;
    this.userRepository = userRepository;
  }

  @DeleteMapping("/players/{id}")
  public ResponseEntity<Void> deletePlayer(@PathVariable Long id) {
    try {
      this.remoteAuthService.deleteUserById(id);
      this.userRepository.deleteById(id);
      return ResponseEntity.noContent().build();
    } catch (UnavailableAuthServiceException e) {
      throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, e.getMessage());
    }
  }

}
