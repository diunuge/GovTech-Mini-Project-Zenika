package com.diunuge.govtech.service;

import com.diunuge.govtech.exception.SessionClosedException;
import com.diunuge.govtech.exception.SessionNotFoundException;
import com.diunuge.govtech.exception.UnauthorizedAccessException;
import com.diunuge.govtech.exception.UserAlreadyJoinedException;
import com.diunuge.govtech.exception.UserNotInvitedException;
import com.diunuge.govtech.model.Restaurant;
import com.diunuge.govtech.model.Session;
import com.diunuge.govtech.model.User;
import java.util.List;

public interface SessionService {

  Session createSession(Session session);
  Session getSessionById(Long id) throws SessionNotFoundException;
  List<Session> getAllSessions();
  List<Session> getSessionsForUser(Long userId);
  void joinSession(Long sessionId, User user)
      throws SessionNotFoundException, UserAlreadyJoinedException, UserNotInvitedException;
  void submitRestaurant(Long sessionId, Restaurant restaurant)
      throws SessionNotFoundException, SessionClosedException;
  void endSession(Long sessionId, Long userId)
      throws SessionNotFoundException, UnauthorizedAccessException, SessionClosedException;

}
