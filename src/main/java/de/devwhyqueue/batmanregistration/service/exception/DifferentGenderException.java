package de.devwhyqueue.batmanregistration.service.exception;

public class DifferentGenderException extends Exception {

  public DifferentGenderException() {
    super("Double players must have same genders!");
  }
}
