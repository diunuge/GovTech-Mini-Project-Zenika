package com.diunuge.govtech.service;

import com.diunuge.govtech.model.Restaurant;
import com.diunuge.govtech.repository.RestaurantRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RestaurantServiceImpl implements RestaurantService{

  private final RestaurantRepository restaurantRepository;

  @Autowired
  public RestaurantServiceImpl(RestaurantRepository restaurantRepository) {
    this.restaurantRepository = restaurantRepository;
  }

  @Override
  public List<Restaurant> getRestaurants() {
    return restaurantRepository.findAll();
  }

  @Override
  public Restaurant addRestaurant(Restaurant restaurant) {
    return this.restaurantRepository.save(restaurant);
  }
}
