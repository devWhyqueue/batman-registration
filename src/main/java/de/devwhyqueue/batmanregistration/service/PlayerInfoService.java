package de.devwhyqueue.batmanregistration.service;

import de.devwhyqueue.batmanregistration.model.Player;
import de.devwhyqueue.batmanregistration.service.exception.UnavailableAuthServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
@Transactional
public class PlayerInfoService {

    private final Logger log = LoggerFactory.getLogger(PlayerInfoService.class);

    @Value("${auth.uri}")
    private String authServer;
    private RestTemplate restTemplate;

    public PlayerInfoService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Player getOwnPlayerInfoFromAuthService() throws UnavailableAuthServiceException {
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

    public Player getPlayerInfoByIdFromAuthService(Long id) throws UnavailableAuthServiceException {
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
}
