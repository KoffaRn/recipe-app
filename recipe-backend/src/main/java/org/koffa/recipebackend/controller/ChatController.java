package org.koffa.recipebackend.controller;

import org.koffa.recipebackend.entity.ChatMessage;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {
    @MessageMapping("/chat/{recipeId}/sendMessage")
    @SendTo("/recipechat/{recipeId}")
    public ChatMessage sendMessage(@DestinationVariable long roomId, ChatMessage chatMessage) {
        return chatMessage;
    }
}
