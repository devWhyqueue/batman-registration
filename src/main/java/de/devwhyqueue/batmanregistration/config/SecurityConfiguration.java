package de.devwhyqueue.batmanregistration.config;

import de.devwhyqueue.batmanregistration.security.jwt.JWTConfigurer;
import de.devwhyqueue.batmanregistration.security.jwt.TokenProvider;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

  private final TokenProvider tokenProvider;

  public SecurityConfiguration(TokenProvider tokenProvider) {
    this.tokenProvider = tokenProvider;
  }

  @Override
  public void configure(HttpSecurity http) throws Exception {
    // @formatter:off
    http
        .csrf()
        .disable()
        .exceptionHandling()
        .and()
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .authorizeRequests()
        .antMatchers("/api/**").authenticated()
        .and()
        .apply(securityConfigurerAdapter());
    // @formatter:on
  }


  private JWTConfigurer securityConfigurerAdapter() {
    return new JWTConfigurer(tokenProvider);
  }
}
