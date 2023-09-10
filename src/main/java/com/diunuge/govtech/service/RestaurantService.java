package com.diunuge.govtech.service;

import com.diunuge.govtech.model.Restaurant;
import java.util.List;

public interface RestaurantService {

  List<Restaurant> getRestaurants();

  Restaurant addRestaurant(Restaurant restaurant);
}
