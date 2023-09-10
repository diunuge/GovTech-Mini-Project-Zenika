package com.diunuge.govtech.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import com.diunuge.govtech.model.Restaurant;
import com.diunuge.govtech.repository.RestaurantRepository;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class RestaurantServiceTest {

  @Mock
  private RestaurantRepository restaurantRepository;

  @InjectMocks
  private RestaurantServiceImpl restaurantService;

  @BeforeEach
  public void initMocks() {

  }

  @Test
  public void testGetRestaurants() {
    // Given
    List<Restaurant> mockRestaurants = new ArrayList<>();
    mockRestaurants.add(
        new Restaurant(1L, "Restaurant A", "description 1")
    );
    mockRestaurants.add(
        new Restaurant(2L, "Restaurant B", "description 2")
    );

    when(restaurantRepository.findAll()).thenReturn(mockRestaurants);

    // When
    List<Restaurant> restaurants = restaurantService.getRestaurants();

    // Assert
    assertEquals(2, restaurants.size());
  }

  @Test
  public void testAddRestaurant() {
    // Given
    Restaurant newRestaurant =
        new Restaurant(null, "New Restaurant", "description");

    when(restaurantRepository.save(newRestaurant))
        .thenReturn(new Restaurant(1L, "New Restaurant", "description"));

    // When
    Restaurant addedRestaurant = restaurantService.addRestaurant(newRestaurant);

    // Assert
    assertEquals(1L, addedRestaurant.getId());
    assertEquals("New Restaurant", addedRestaurant.getName());
  }
}
