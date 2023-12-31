package org.koffa.recipefrontend.pojo;

import lombok.Data;

import java.sql.Timestamp;
@Data
public class ChatMessage implements Comparable<ChatMessage> {
    private String sender;
    private String message;
    private Timestamp timestamp;

    @Override
    public int compareTo(ChatMessage o) {
        return timestamp.compareTo(o.getTimestamp());
    }
}