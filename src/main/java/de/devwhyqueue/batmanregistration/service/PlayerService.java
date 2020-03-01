package de.devwhyqueue.batmanregistration.service;

import de.devwhyqueue.batmanregistration.model.Player;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class PlayerService {

    private RestTemplate restTemplate;

    public PlayerService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Optional<Player> getPlayerInfoFromAuthService() {
        ResponseEntity<Player> response = restTemplate.exchange(
                "http://localhost:8090/api/users/self", HttpMethod.GET, null,
                new ParameterizedTypeReference<Player>(){});

        return Optional.of(response.getBody());
    }
}
