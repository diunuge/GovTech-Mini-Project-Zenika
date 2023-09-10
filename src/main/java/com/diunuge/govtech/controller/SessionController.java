package com.diunuge.govtech.controller;

import com.diunuge.govtech.exception.SessionClosedException;
import com.diunuge.govtech.exception.SessionNotFoundException;
import com.diunuge.govtech.exception.UnauthorizedAccessException;
import com.diunuge.govtech.exception.UserAlreadyJoinedException;
import com.diunuge.govtech.exception.UserNotInvitedException;
import com.diunuge.govtech.model.Restaurant;
import com.diunuge.govtech.model.Session;
import com.diunuge.govtech.model.User;
import com.diunuge.govtech.service.SessionService;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/v1/session")
@CrossOrigin("*")
public class SessionController {

  private final SessionService sessionService;

  @Autowired
  public SessionController(SessionService sessionService) {
    this.sessionService = sessionService;
  }

  @GetMapping
  List<Session> getAllSessions(){
    return sessionService.getAllSessions();
  }

  @GetMapping("/forUser/{userId}")
  public List<Session> getSessionsForUser(@PathVariable Long userId) {
    return sessionService.getSessionsForUser(userId);
  }

  @PostMapping
  ResponseEntity<Session> createSession(@RequestBody Session session) {
     Session createdSession = sessionService.createSession(session);
    return new ResponseEntity<>(createdSession, HttpStatus.CREATED);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Session> getSessionById(@PathVariable Long id) {
    try {
      Session session = sessionService.getSessionById(id);
      return new ResponseEntity<>(session, HttpStatus.OK);
    } catch (SessionNotFoundException e) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @PostMapping("/{sessionId}/join")
  public ResponseEntity<Void> joinSession(
      @PathVariable Long sessionId,
      @RequestBody User user
  ) {
    try {
      sessionService.joinSession(sessionId, user);
      return new ResponseEntity<>(HttpStatus.CREATED);
    } catch (SessionNotFoundException e) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    } catch (UserAlreadyJoinedException | UserNotInvitedException e) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
  }

  @PostMapping("/{sessionId}/submit")
  public ResponseEntity<Void> submitRestaurant(
      @PathVariable Long sessionId,
      @RequestBody Restaurant restaurant
  ) {
    try {
      sessionService.submitRestaurant(sessionId, restaurant);
      return new ResponseEntity<>(HttpStatus.CREATED);
    } catch (SessionNotFoundException e) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    } catch (SessionClosedException e) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
  }

  @PostMapping("/{sessionId}/end")
  public ResponseEntity<Void> endSession(
      @PathVariable Long sessionId,
      @RequestParam Long userId
  ) {
    try {
      sessionService.endSession(sessionId, userId);
      return new ResponseEntity<>(HttpStatus.OK);
    } catch (SessionNotFoundException e) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    } catch (UnauthorizedAccessException | SessionClosedException e) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
  }

}
