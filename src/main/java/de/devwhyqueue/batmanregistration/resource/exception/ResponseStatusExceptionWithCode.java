package de.devwhyqueue.batmanregistration.resource.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ResponseStatusExceptionWithCode extends ResponseStatusException {

  private String code;

  public ResponseStatusExceptionWithCode(HttpStatus status,
      String reason, String code) {
    super(status, reason);
    this.code = code;
  }

  public String getCode() {
    return code;
  }
}
