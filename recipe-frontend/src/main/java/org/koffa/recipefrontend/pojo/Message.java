package org.koffa.recipefrontend.pojo;

import lombok.Data;

@Data
public class Message {
    private String timestamp;
    private String sender;
    private String message;
}
