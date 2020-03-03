package de.devwhyqueue.batmanregistration.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.annotation.RequestScope;

@Component
public class TokenProvider implements InitializingBean {

  private final Logger log = LoggerFactory.getLogger(TokenProvider.class);

  private static final String AUTHORITIES_KEY = "auth";

  private Key key;

  private final JWTProperties jwtProperties;

  public TokenProvider(JWTProperties jwtProperties) {
    this.jwtProperties = jwtProperties;
  }

  @Override
  public void afterPropertiesSet() throws Exception {
    byte[] keyBytes;
    String secret = jwtProperties.getBase64Secret();
    log.debug("Using a Base64-encoded JWT secret key");
    keyBytes = Decoders.BASE64.decode(jwtProperties.getBase64Secret());
    this.key = Keys.hmacShaKeyFor(keyBytes);
  }

  public Authentication getAuthentication(String token) {
    Claims claims = Jwts.parser()
        .setSigningKey(key)
        .parseClaimsJws(token)
        .getBody();

    Collection<? extends GrantedAuthority> authorities =
        Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
            .map(SimpleGrantedAuthority::new)
            .collect(Collectors.toList());

    User principal = new User(claims.getSubject(), "", authorities);

    return new UsernamePasswordAuthenticationToken(principal, token, authorities);
  }

  public boolean validateToken(String authToken) {
    try {
      Jwts.parser().setSigningKey(key).parseClaimsJws(authToken);
      return true;
    } catch (JwtException | IllegalArgumentException e) {
      log.info("Invalid JWT token.");
      log.trace("Invalid JWT token trace.", e);
    }
    return false;
  }

  @Bean
  @RequestScope
  public RestTemplate restTemplate(HttpServletRequest inReq) {
    // retrieve the auth header from incoming request
    final String authHeader =
        inReq.getHeader(HttpHeaders.AUTHORIZATION);
    SimpleClientHttpRequestFactory httpRequestFactory = new SimpleClientHttpRequestFactory();
    httpRequestFactory.setConnectTimeout(3000);
    final RestTemplate restTemplate = new RestTemplate(httpRequestFactory);
    // add a token if an incoming auth header exists, only
    if (authHeader != null && !authHeader.isEmpty()) {
      // since the header should be added to each outgoing request,
      // add an interceptor that handles this.
      restTemplate.getInterceptors().add(
          (outReq, bytes, clientHttpReqExec) -> {
            outReq.getHeaders().set(
                HttpHeaders.AUTHORIZATION, authHeader
            );
            return clientHttpReqExec.execute(outReq, bytes);
              });
    }
    return restTemplate;
  }
}
