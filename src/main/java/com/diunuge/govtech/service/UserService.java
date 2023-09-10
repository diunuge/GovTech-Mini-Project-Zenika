package com.diunuge.govtech.service;

import com.diunuge.govtech.model.User;
import java.util.List;

public interface UserService {

  List<User> getUsers();
  User addUser(User user);
  User getUserById(Long id);
  User getUserByUsername(String userName);

}