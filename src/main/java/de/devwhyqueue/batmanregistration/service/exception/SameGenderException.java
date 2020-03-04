package de.devwhyqueue.batmanregistration.service.exception;

public class SameGenderException extends Exception {

  public SameGenderException() {
    super("Mixed players must have different genders!");
  }
}
