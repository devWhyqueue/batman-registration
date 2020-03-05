package de.devwhyqueue.batmanregistration.resource.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Getter
public class ResponseStatusExceptionWithCode extends ResponseStatusException {

  private String code;

  public ResponseStatusExceptionWithCode(HttpStatus status,
      String reason, String code) {
    super(status, reason);
    this.code = code;
  }
}
