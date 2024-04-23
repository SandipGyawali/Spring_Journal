package com.example.JournalApp.controller;

import com.example.JournalApp.entity.User;
import com.example.JournalApp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> getAllUsers(){
        return userService.getAllUser();
    }

    @PostMapping
    public void createUser(@RequestBody User user){
        userService.saveUser(user);
    }

    @PutMapping("{id}")
    public ResponseEntity<?> updateUserById(@PathVariable ObjectId id, @RequestBody User user){
        Optional<User> userExists = userService.getUserById(id);

        if(userExists.isPresent()){
            User existingUser = userExists.get();
            existingUser.setUsername(user.getUsername());
            existingUser.setPassword(user.getPassword());
            userService.saveUser(existingUser);

            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
