package com.diunuge.govtech.config;

import com.diunuge.govtech.model.User;
import com.diunuge.govtech.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DatabaseInitializer implements CommandLineRunner {
  private final UserRepository userRepository;

  @Autowired
  public DatabaseInitializer(
      UserRepository userRepository
  ) {
    this.userRepository = userRepository;
  }

  @Override
  public void run(String... args) {

    userRepository.save(User.builder().username("Diunuge").build());
    userRepository.save(User.builder().username("Buddhika").build());
    userRepository.save(User.builder().username("Wijesinghe").build());
  }
}
