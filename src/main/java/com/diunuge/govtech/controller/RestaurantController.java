package com.diunuge.govtech.controller;

import com.diunuge.govtech.model.Restaurant;
import com.diunuge.govtech.service.RestaurantService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/v1/restaurant")
@CrossOrigin("*")
public class RestaurantController {

  private final RestaurantService restaurantService;

  public RestaurantController(RestaurantService restaurantService) {
    this.restaurantService = restaurantService;
  }

  @GetMapping
  public List<Restaurant> getRestaurants(){
    return this.restaurantService.getRestaurants();
  }

  @PostMapping
  public ResponseEntity<Void> addRestaurant(@RequestBody Restaurant restaurant){

    try {
      this.restaurantService.addRestaurant(restaurant);
      return new ResponseEntity<>(HttpStatus.CREATED);
    } catch (RuntimeException e) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
  }
}
