package com.diunuge.govtech.config;

import com.diunuge.govtech.model.Restaurant;
import com.diunuge.govtech.model.User;
import com.diunuge.govtech.repository.RestaurantRepository;
import com.diunuge.govtech.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DatabaseInitializer implements CommandLineRunner {
  private final UserRepository userRepository;
  private final RestaurantRepository restaurantRepository;

  @Autowired
  public DatabaseInitializer(
      UserRepository userRepository,
      RestaurantRepository restaurantRepository
  ) {
    this.userRepository = userRepository;
    this.restaurantRepository = restaurantRepository;
  }

  @Override
  public void run(String... args) {

    userRepository.save(User.builder().username("Diunuge").build());
    userRepository.save(User.builder().username("Buddhika").build());
    userRepository.save(User.builder().username("Wijesinghe").build());

    restaurantRepository.save(
        Restaurant.builder().name("Steam Boat").description("Asian restaurant").build()
    );
    restaurantRepository.save(
        Restaurant.builder().name("Fiz").description("Chinese restaurant").build()
    );
    restaurantRepository.save(
        Restaurant.builder().name("Sea Joint").description("Sea food restaurant").build()
    );
  }
}
