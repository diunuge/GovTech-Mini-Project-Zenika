package com.diunuge.govtech.exception;

public class SessionNotFoundException extends RuntimeException{

  public SessionNotFoundException(Long id) {
    super("Session not found with ID: " + id);
  }
}
