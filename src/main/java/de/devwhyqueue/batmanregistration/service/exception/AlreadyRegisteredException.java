package de.devwhyqueue.batmanregistration.service.exception;

public class AlreadyRegisteredException extends Exception {

  public AlreadyRegisteredException() {
    super("Already registered or cancelled before!");
  }
}
