package com.example.JournalApp.service;

import com.example.JournalApp.entity.JournalEntry;
import com.example.JournalApp.entity.User;
import com.example.JournalApp.repository.UserRepo;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class UserService {
    @Autowired
    private UserRepo userRepo;

    public void saveUser(User newUser){
        userRepo.save(newUser);
    }

    public List<User> getAllUser(){
        return userRepo.findAll();
    }

    public Optional<User> getUserById(ObjectId id){
        return userRepo.findById(id);
    }

    public void deleteUserById(ObjectId id){
        userRepo.deleteById(id);
    }

    public User findByUserName(String username){
        return userRepo.findByUsername(username);
    }
}
