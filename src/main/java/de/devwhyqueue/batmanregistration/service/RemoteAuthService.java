package de.devwhyqueue.batmanregistration.service;

import de.devwhyqueue.batmanregistration.model.Player;
import de.devwhyqueue.batmanregistration.service.exception.UnavailableAuthServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
public class RemoteAuthService {

  private final Logger log = LoggerFactory.getLogger(RemoteAuthService.class);

  @Value("${auth.url}")
  private String authServer;
  private RestTemplate restTemplate;
  private RestTemplate systemRestTemplate;

  public RemoteAuthService(
      @Qualifier("restTemplateWithToken") RestTemplate restTemplate,
      @Qualifier("restTemplateWithSystemToken") RestTemplate systemRestTemplate) {
    this.restTemplate = restTemplate;
    this.systemRestTemplate = systemRestTemplate;
  }

  public Player getOwnUserInfo() throws UnavailableAuthServiceException {
    try {
      ResponseEntity<Player> response = restTemplate.exchange(
          authServer + "users/self", HttpMethod.GET, null,
          new ParameterizedTypeReference<>() {
          });
      return response.getBody();
    } catch (RestClientException ex) {
      log.error("Could not reach authentication service at {}", authServer);
      throw new UnavailableAuthServiceException();
    }
  }

  public Player getPlayerInfoById(Long id) throws UnavailableAuthServiceException {
    try {
      ResponseEntity<Player> response = restTemplate.exchange(
          authServer + "users/" + id, HttpMethod.GET, null,
          new ParameterizedTypeReference<>() {
          });
      return response.getBody();
    } catch (RestClientException ex) {
      log.error("Could not reach authentication service at {}", authServer);
      throw new UnavailableAuthServiceException();
    }
  }

  public void deleteOwnUser() throws UnavailableAuthServiceException {
    try {
      systemRestTemplate.exchange(
          authServer + "users/self", HttpMethod.DELETE, null,
          new ParameterizedTypeReference<>() {
          });
    } catch (RestClientException ex) {
      log.error("Could not reach authentication service at {}", authServer);
      throw new UnavailableAuthServiceException();
    }
  }
}
