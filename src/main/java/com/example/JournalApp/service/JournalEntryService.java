package com.example.JournalApp.service;

import com.example.JournalApp.entity.*;
import com.example.JournalApp.repository.JournalEntryRepo;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class JournalEntryService {
    @Autowired
    private JournalEntryRepo journalEntryRepo;

    @Autowired
    private UserService userService;

    public JournalEntry saveEntry(JournalEntry entry, String username){
        User user = userService.findByUserName(username);

        entry.setDate(new Date());
        JournalEntry saved = journalEntryRepo.save(entry);
        user.getJournalEntries().add(saved);
        userService.saveUser(user);
        return saved;
    }

    public List<JournalEntry> getAll(){
        return journalEntryRepo.findAll();
    }

    public Optional<JournalEntry> findById(ObjectId id){
        return journalEntryRepo.findById(id);
    }

    public void removeEntry(ObjectId id, String username){
        User user = userService.findByUserName(username);
        user.getJournalEntries().removeIf(x -> x.getId().equals(id));
        userService.saveUser(user);
        journalEntryRepo.deleteById(id);
    }

    public void saveEntry(JournalEntry journalEntry) {
        journalEntryRepo.save(journalEntry);
    }
}
