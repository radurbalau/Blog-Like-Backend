package com.myspringappbackend.myspringappbackend.controller;

import com.myspringappbackend.myspringappbackend.models.User;
import com.myspringappbackend.myspringappbackend.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class UserController {

    private final UserRepository userRepository;

    @PostMapping("/users/register")
    public ResponseEntity<Object> registerUser(@RequestBody User user){
        User user1  = userRepository.save(user);
        return  new ResponseEntity<Object>("Added user with name :" + user.getUsername(),HttpStatus.OK);
    }

    @GetMapping("/users/login")
    public ResponseEntity<Object> sign_inUser(@RequestParam(name = "username",required = false) String username
            , @RequestParam(name = "password",required = false) String password){
        Optional<User> user  = Optional.ofNullable(userRepository.findUserByUsernameAndPassword(username, password));
        if(user.isEmpty())
            return  new ResponseEntity<>("User Not found in database",HttpStatus.NOT_FOUND);

        return  new ResponseEntity<Object>("You logged in as user : " + username, HttpStatus.OK);
    }

    @GetMapping("/users")
    public ResponseEntity<Object> getAllUsers(){
        return ResponseEntity.status(HttpStatus.OK).
                body(userRepository.findAll());
    }

    @DeleteMapping("/admin/{userId}")
    public ResponseEntity<Object> deleteUserById(@PathVariable Integer userId){
        Optional<User> user = userRepository.findById(userId);

        if(user.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User with this id not found");

        if(user.get().getIsAdmin() == null){
            return ResponseEntity.status(HttpStatus.OK).body("You deleted user with id : " + userId);

        }

            userRepository.deleteById(userId);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("YOU CANT DELETE ADMIN");

    }


}
