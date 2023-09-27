package org.koffa.recipefrontend.service;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class ChatService {
    private final SimpMessagingTemplate messagingTemplate;

    public ChatService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }
    public void sendMessage(long roomId, String message) {
        messagingTemplate.convertAndSend("/recipechat/" + roomId, message);
    }
}
