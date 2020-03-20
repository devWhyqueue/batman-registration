package de.devwhyqueue.batmanregistration.security.jwt;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "jwt")
@Getter
@Setter
public class JWTProperties {
  private int tokenValiditySec;
  private String base64Secret;
}
