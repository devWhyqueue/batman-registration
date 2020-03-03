package de.devwhyqueue.batmanregistration.service;

import de.devwhyqueue.batmanregistration.model.Player;
import java.util.Optional;
import javax.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
@Transactional
public class PlayerService {

    private final Logger log = LoggerFactory.getLogger(PlayerService.class);

    @Value("${auth.uri}")
    private String authServer;
    private RestTemplate restTemplate;

    public PlayerService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Optional<Player> getOwnPlayerInfoFromAuthService() {
        try {
            ResponseEntity<Player> response = restTemplate.exchange(
                authServer + "users/self", HttpMethod.GET, null,
                new ParameterizedTypeReference<>() {
                });
            return Optional.ofNullable(response.getBody());
        } catch (RestClientException ex) {
            log.error("Could not reach authentication service at {}", authServer);
            return Optional.empty();
        }
    }

    public Optional<Player> getPlayerInfoByIdFromAuthService(Long id) {
        try {
            ResponseEntity<Player> response = restTemplate.exchange(
                authServer + "users/" + id, HttpMethod.GET, null,
                new ParameterizedTypeReference<>() {
                });
            return Optional.ofNullable(response.getBody());
        } catch (RestClientException ex) {
            ex.printStackTrace();
            log.error("Could not reach authentication service at {}", authServer);
            return Optional.empty();
        }
    }
}
