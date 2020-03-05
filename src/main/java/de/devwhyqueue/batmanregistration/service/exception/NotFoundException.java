package de.devwhyqueue.batmanregistration.service.exception;

public class NotFoundException extends Exception {

  public NotFoundException() {
    super("Entity was not found!");
  }
}
