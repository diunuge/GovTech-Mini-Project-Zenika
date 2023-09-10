package com.diunuge.govtech.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import com.diunuge.govtech.exception.UserNotFoundException;
import com.diunuge.govtech.model.User;
import com.diunuge.govtech.repository.UserRepository;
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
public class UserServiceTest {

  @Mock
  private UserRepository userRepository;

  @InjectMocks
  private UserServiceImpl userService;

  @Test
  public void testGetUsers() {
    // Given
    List<User> mockUsers = new ArrayList<>();
    mockUsers.add(User.builder().username("Diunuge").build());
    mockUsers.add(User.builder().username("Buddhika").build());


    when(userRepository.findAll()).thenReturn(mockUsers);

    // When
    List<User> users = userService.getUsers();

    // Assert
    assertEquals(2, users.size());
  }

  @Test
  public void testAddUser() {
    // Given
    User newUser = User.builder().username("Diunuge").build();

    when(userRepository.save(newUser)).thenReturn(newUser);

    // When
    User addedUser = userService.addUser(newUser);

    // Assert
    assertEquals(newUser, addedUser);
  }

  @Test
  public void testGetUserByUsername() {
    // Given
    String username = "existingUser";
    User existingUser = new User(1L, username);

    when(userRepository.findByUsername(username)).thenReturn(Optional.of(existingUser));

    // When
    User foundUser = userService.getUserByUsername(username);

    // Assert
    assertEquals(existingUser, foundUser);
  }

  @Test
  public void testGetUserByUsernameNotFound() {
    // Given
    String username = "nonExistingUser";

    when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

    // When and Assert
    UserNotFoundException exception = assertThrows(
        UserNotFoundException.class,
        () ->  userService.getUserByUsername(username)
    );

    assertEquals("User not found with username: " + username, exception.getMessage());
  }

  @BeforeEach
  public void initMocks() {

  }
}
