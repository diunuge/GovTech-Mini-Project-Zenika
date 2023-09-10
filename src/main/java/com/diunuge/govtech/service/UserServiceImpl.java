package com.diunuge.govtech.service;

import com.diunuge.govtech.exception.UserNotFoundException;
import com.diunuge.govtech.model.User;
import com.diunuge.govtech.repository.UserRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

  private final UserRepository userRepository;

  @Autowired
  public UserServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public List<User> getUsers() {
    return this.userRepository.findAll();
  }

  @Override
  public User getUserByUsername(String username) {
    Optional<User> userOptional = userRepository.findByUsername(username);
    return userOptional.orElseThrow(() -> new UserNotFoundException(username));
  }

  @Override
  public User addUser(User user) {
    return this.userRepository.save(user);
  }

  @Override
  public User getUserById(Long id) {
    Optional<User> userOptional = userRepository.findById(id);
    return userOptional.orElseThrow(() -> new UserNotFoundException(id));
  }
}

