package org.koffa.recipebackend.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Entity
public class ChatMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private Timestamp timestamp;
    private String sender;
    private String message;
    private Long recipeId;
}