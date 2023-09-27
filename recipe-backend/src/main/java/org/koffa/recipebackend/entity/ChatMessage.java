package org.koffa.recipebackend.entity;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class ChatMessage {
    private String sender;
    private String message;
}
