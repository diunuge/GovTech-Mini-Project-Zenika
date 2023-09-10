package com.diunuge.govtech.exception;

public class SessionClosedException extends RuntimeException {

  public SessionClosedException() {
    super("Session is already closed!");
  }
}
