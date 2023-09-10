package com.diunuge.govtech.service;

import com.diunuge.govtech.exception.SessionClosedException;
import com.diunuge.govtech.exception.SessionNotFoundException;
import com.diunuge.govtech.exception.UnauthorizedAccessException;
import com.diunuge.govtech.exception.UserAlreadyJoinedException;
import com.diunuge.govtech.exception.UserNotFoundException;
import com.diunuge.govtech.exception.UserNotInvitedException;
import com.diunuge.govtech.model.Restaurant;
import com.diunuge.govtech.model.Session;
import com.diunuge.govtech.model.User;
import com.diunuge.govtech.repository.SessionRepository;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SessionServiceImpl implements SessionService {

  private final SessionRepository sessionRepository;

  @Autowired
  public SessionServiceImpl(SessionRepository sessionRepository) {
    this.sessionRepository = sessionRepository;
  }

  @Override
  public Session createSession(Session session) {

    return sessionRepository.save(session);
  }

  @Override
  public Session getSessionById(Long id) throws SessionNotFoundException {
    Optional<Session> optionalSession = sessionRepository.findById(id);
    if (optionalSession.isPresent()) {
      return optionalSession.get();
    } else {
      throw new SessionNotFoundException(id);
    }
  }

  @Override
  public List<Session> getAllSessions() {
    return sessionRepository.findAll();
  }

  @Override
  public List<Session> getSessionsForUser(Long userId) {
    return sessionRepository.findSessionsForUser(userId);
  }

  @Override
  public void joinSession(Long sessionId, User user)
      throws SessionNotFoundException, UserAlreadyJoinedException, UserNotInvitedException {

    Session session = getSessionById(sessionId);
    User userToJoin;

    if(session == null){
      throw new SessionNotFoundException(sessionId);
    }

    if(user.getId() == null){
      throw new UserNotFoundException("");
    }
    else{
      Optional<User> userToJoinOptional = session.getParticipantsInvited()
          .stream()
          .filter(invitedUser -> invitedUser.getId().equals(user.getId())) // userId is the ID you want to find
          .findFirst();

      if (userToJoinOptional.isEmpty()) {
        throw new UserNotInvitedException();
      }else{
        userToJoin = userToJoinOptional.get();
      }

      Optional<User> userJoinedOptional = session.getParticipants()
          .stream()
          .filter(participant -> participant.getId().equals(user.getId())) // userId is the ID you want to find
          .findFirst();

      if (userJoinedOptional.isPresent()) {
        throw new UserAlreadyJoinedException();
      }
    }

    session.getParticipants().add(userToJoin);
    sessionRepository.save(session);
  }

  @Override
  public void submitRestaurant(Long sessionId, Restaurant restaurant)
      throws SessionNotFoundException, SessionClosedException {
    Session session = getSessionById(sessionId);

    if (session.isClosed()) {
      throw new SessionClosedException();
    }

    if(!session.getSubmittedRestaurants().contains(restaurant)){
      session.getSubmittedRestaurants().add(restaurant);
      sessionRepository.save(session);
    }
  }

  @Override
  public void endSession(Long sessionId, Long userId)
      throws SessionNotFoundException, UnauthorizedAccessException, SessionClosedException {
    Session session = getSessionById(sessionId);

    if (session.isClosed()) {
      throw new SessionClosedException();
    }

    // Check if the user initiating the end session is the session creator
    if (!session.getInitiator().getId().equals(userId)) {
      throw new UnauthorizedAccessException("You are not authorized to end this session.");
    }

    List<Restaurant> submittedRestaurants = session.getSubmittedRestaurants();

    if(!submittedRestaurants.isEmpty()){
      Random random = new Random();
      Restaurant selectedRestaurant = submittedRestaurants.get(random.nextInt(submittedRestaurants.size()));
      session.setSelectedRestaurant(selectedRestaurant);
      sessionRepository.save(session);
    }else{
      //throw
    }
  }
}
