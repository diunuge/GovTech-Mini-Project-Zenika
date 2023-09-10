package com.diunuge.govtech.controller;

import com.diunuge.govtech.exception.SessionNotFoundException;
import com.diunuge.govtech.model.User;
import com.diunuge.govtech.service.UserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/v1/user")
@CrossOrigin("*")
public class UserController {

  private final UserService userService;

  @Autowired
  public UserController(UserService userService){
    this.userService = userService;
  }

  @GetMapping
  public List<User> getUsers(){
    return this.userService.getUsers();
  }

  @PostMapping
  public ResponseEntity<User> createUser(@RequestBody User user){
    try {
      User userCreated = userService.addUser(user);
      return new ResponseEntity<>(userCreated, HttpStatus.CREATED);
    } catch (SessionNotFoundException e) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
  }

  @GetMapping("/{username}")
  public ResponseEntity<User> getUserByUsername(@PathVariable String username) {
    try {
      User user = userService.getUserByUsername(username);
      return new ResponseEntity<>(user, HttpStatus.OK);
    } catch (SessionNotFoundException e) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

}
