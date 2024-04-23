package com.example.JournalApp.entity;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "journal_entries")
@Getter
@Setter
@ToString
public class JournalEntry {
    @Id
    private ObjectId id;
    private String title;
    private String content;
    private Date date;
}