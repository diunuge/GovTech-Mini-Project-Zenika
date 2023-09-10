package com.diunuge.govtech.exception;

public class UserAlreadyJoinedException extends RuntimeException {

  public UserAlreadyJoinedException() {
    super("User is already part of this session.");
  }
}
