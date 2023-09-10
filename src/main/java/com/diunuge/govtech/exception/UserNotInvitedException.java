package com.diunuge.govtech.exception;

public class UserNotInvitedException extends RuntimeException {

  public UserNotInvitedException() {
    super("User is not invited to this session.");
  }
}
