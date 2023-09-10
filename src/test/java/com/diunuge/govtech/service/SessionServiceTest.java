package com.diunuge.govtech.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import com.diunuge.govtech.exception.SessionNotFoundException;
import com.diunuge.govtech.model.Restaurant;
import com.diunuge.govtech.model.Session;
import com.diunuge.govtech.model.User;
import com.diunuge.govtech.repository.SessionRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class SessionServiceTest {

  @Mock
  private SessionRepository sessionRepository;

  @InjectMocks
  private SessionServiceImpl sessionService;

  @BeforeEach
  public void initMocks() {

  }

  @Test
  public void testCreateSession() {
    // Given
    Session sessionToCreate = new Session(/* Initialize session object as needed */);

    when(sessionRepository.save(sessionToCreate)).thenReturn(sessionToCreate);

    // When
    Session createdSession = sessionService.createSession(sessionToCreate);

    // Assert
    assertEquals(sessionToCreate, createdSession);
  }

  @Test
  public void testGetSessionById() {
    // Given
    Long sessionId = 1L;
    Session mockSession = new Session(/* Initialize session object as needed */);

    when(sessionRepository.findById(sessionId)).thenReturn(Optional.of(mockSession));

    // When
    Session retrievedSession = sessionService.getSessionById(sessionId);

    // Assert
    assertEquals(mockSession, retrievedSession);
  }

  @Test
  public void testGetSessionByIdNotFound() {
    // Given
    Long sessionId = 1L;

    when(sessionRepository.findById(sessionId)).thenReturn(Optional.empty());

    // When and Assert
    assertThrows(SessionNotFoundException.class, () -> sessionService.getSessionById(sessionId));
  }

  @Test
  public void testGetAllSessions() {
    // Given
    List<Session> mockSessions = new ArrayList<>();
    mockSessions.add(new Session(/* Initialize session objects as needed */));
    mockSessions.add(new Session(/* Initialize session objects as needed */));

    when(sessionRepository.findAll()).thenReturn(mockSessions);

    // When
    List<Session> retrievedSessions = sessionService.getAllSessions();

    // Assert
    assertEquals(2, retrievedSessions.size());
  }

  @Test
  public void testGetSessionsForUser() {
    // Given
    Long userId = 1L;
    List<Session> mockSessions = new ArrayList<>();
    mockSessions.add(new Session(/* Initialize session objects as needed */));
    mockSessions.add(new Session(/* Initialize session objects as needed */));

    when(sessionRepository.findSessionsForUser(userId)).thenReturn(mockSessions);

    // When
    List<Session> retrievedSessions = sessionService.getSessionsForUser(userId);

    // Assert
    assertEquals(2, retrievedSessions.size());
  }

  @Test
  public void testJoinSessionNotFound() {
    // Given
    Long sessionId = 1L;
    User user = User.builder().id(1L).username("Diu").build();

    when(sessionRepository.findById(sessionId)).thenReturn(Optional.empty());

    // When and Assert
    assertThrows(SessionNotFoundException.class, () -> sessionService.joinSession(sessionId, user));
  }

  @Test
  public void testJoinSession() {
    // Given
    Long sessionId = 1L;
    User user = User.builder().id(1L).username("Diu").build();
    User user2 = User.builder().id(2L).username("Bud").build();

    List<User> invitedParticipants = new ArrayList<>();
    invitedParticipants.add(user);

    List<User> participants = new ArrayList<>();
    participants.add(user2);

    Session session = Session.builder()
        .id(1L)
        .name("Test session")
        .participantsInvited(invitedParticipants)
        .participants(participants)
        .build();

    when(sessionRepository.findById(sessionId))
        .thenReturn(Optional.of(session));

    // When and Assert
    assertDoesNotThrow(() -> sessionService.joinSession(sessionId, user));
  }

  @Test
  public void testSubmitRestaurant() {
    // Given
    Long sessionId = 1L;
    Restaurant restaurant =
        Restaurant.builder().name("Steam Boat").description("Asian restaurant").build();

    Session session = Session.builder()
        .id(1L)
        .name("Test session")
        .submittedRestaurants(new ArrayList<>())
        .build();

    when(sessionRepository.findById(sessionId))
        .thenReturn(Optional.of(session));

    // When and Assert
    assertDoesNotThrow(() -> sessionService.submitRestaurant(sessionId, restaurant));

    // Verify that the session's restaurant is set correctly
    assertTrue(session.getSubmittedRestaurants().contains(restaurant));
  }

  @Test
  public void testEndSession() {
    // Given
    Long sessionId = 1L;
    Long userId = 1L;
    User user = User.builder().id(1L).username("Diu").build();
    Restaurant restaurant =
        Restaurant.builder().name("Steam Boat").description("Asian restaurant").build();

    Session session = Session.builder()
        .id(1L)
        .name("Test session")
        .submittedRestaurants(List.of(restaurant))
        .initiator(user)
        .build();

    when(sessionRepository.findById(sessionId))
        .thenReturn(Optional.of(session));

    // When and Assert
    assertDoesNotThrow(() -> sessionService.endSession(sessionId, userId));

    assertTrue(session.isClosed());
    assertNotNull(session.getSelectedRestaurant());
  }

}
