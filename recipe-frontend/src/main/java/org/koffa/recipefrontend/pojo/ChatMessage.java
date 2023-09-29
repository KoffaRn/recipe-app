package org.koffa.recipefrontend.pojo;

import lombok.Data;

import java.sql.Timestamp;
@Data
public class ChatMessage {
    private String sender;
    private String message;
    private Timestamp timestamp;
}
