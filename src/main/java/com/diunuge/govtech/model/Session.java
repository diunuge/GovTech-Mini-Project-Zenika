package com.diunuge.govtech.model;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Session {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String name;

  @ManyToOne
  @JoinColumn(name = "user_id")
  @NotNull(message = "Initiator UUID is required")
  private User initiator;

  @ManyToMany
  @JoinTable(
      name = "session_participants_invited",
      joinColumns = @JoinColumn(name = "session_id"),
      inverseJoinColumns = @JoinColumn(name = "user_id")
  )
  private List<User> participantsInvited;

  @ManyToMany
  @JoinTable(
      name = "session_participants",
      joinColumns = @JoinColumn(name = "session_id"),
      inverseJoinColumns = @JoinColumn(name = "user_id")
  )
  private List<User> participants;

  @ManyToMany
  @JoinTable(
      name = "session_restaurants",
      joinColumns = @JoinColumn(name = "session_id"),
      inverseJoinColumns = @JoinColumn(name = "restaurant_id")
  )
  private List<Restaurant> submittedRestaurants;

  @ManyToOne
  private Restaurant selectedRestaurant;

  @Version
  private Long version;

  public boolean isClosed() {
    return selectedRestaurant != null;
  }
}
