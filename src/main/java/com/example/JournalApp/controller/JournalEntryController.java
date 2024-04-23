package com.example.JournalApp.controller;

import com.example.JournalApp.entity.*;
import com.example.JournalApp.service.*;
import org.bson.types.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;
    @Autowired
    private UserService userService;

    @GetMapping("{username}")
    public ResponseEntity<List<JournalEntry>> getAllJournalOfUser(@PathVariable String username){
        //get all the journal entries
        try{
            User existingUser = userService.findByUserName(username);

            if(existingUser != null){

            List<JournalEntry> all = existingUser.getJournalEntries();

            return new ResponseEntity<>(all, HttpStatus.OK);

            }else{
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

        }catch(Exception err){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("{username}")
    public ResponseEntity<?> createEntry(@RequestBody JournalEntry entry, @PathVariable String username){
        try{
            journalEntryService.saveEntry(entry, username);

            return new ResponseEntity<>(entry, HttpStatus.CREATED);
        }catch(Exception err){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("id/{id}")
    public ResponseEntity<JournalEntry> getJournalEntryById(@PathVariable ObjectId id){
        try{
            Optional<JournalEntry> journalEntry = journalEntryService.findById(id);

            if(journalEntry.isPresent()){
                return new ResponseEntity<>(journalEntry.get(), HttpStatus.OK);
            }

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch(Exception err){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("id/{username}/{id}")
    public ResponseEntity<JournalEntry> remove(@PathVariable ObjectId id, @PathVariable String username){
        try{
            journalEntryService.removeEntry(id, username);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch(Exception err){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/id/{username}/{id}")
    public ResponseEntity<JournalEntry> updateJournalEntry(
            @PathVariable ObjectId id,
            @RequestBody JournalEntry entry,
            @PathVariable String username
    ){
        try{
            JournalEntry old =  journalEntryService.findById(id).orElse(null);
            if(old != null){
                old.setTitle(entry.getTitle() != null ? entry.getTitle() : old.getTitle());
                old.setContent(entry.getContent() != null ? entry.getContent() : old.getContent());
            }

            journalEntryService.saveEntry(old);
            return new ResponseEntity<>(old, HttpStatus.OK);
        }catch(Exception err){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
