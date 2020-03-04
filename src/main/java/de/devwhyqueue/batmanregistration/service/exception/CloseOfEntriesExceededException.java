package de.devwhyqueue.batmanregistration.service.exception;

public class CloseOfEntriesExceededException extends Exception {

  public CloseOfEntriesExceededException() {
    super("Close of entries exceeded!");
  }
}
