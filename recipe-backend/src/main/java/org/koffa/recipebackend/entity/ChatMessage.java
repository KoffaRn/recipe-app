package org.koffa.recipebackend.entity;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class ChatMessage {
    private Timestamp timestamp;
    private String sender;
    private String message;
    private long recipeId;
}
