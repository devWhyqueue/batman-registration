package de.devwhyqueue.batmanregistration.service.exception;

public class UnavailableAuthServiceException extends Exception {

  public UnavailableAuthServiceException() {
    super("Could not get user data from authentication service!");
  }
}
