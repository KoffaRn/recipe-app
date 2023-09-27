package org.koffa.recipebackend.controller;

import org.koffa.recipebackend.entity.ChatMessage;
import org.koffa.recipebackend.entity.Message;
import org.koffa.recipebackend.service.RecipeService;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.sql.Timestamp;
import java.time.Instant;

@Controller
public class ChatController {
    RecipeService recipeService;
    public ChatController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }
    @MessageMapping("/chat/{recipeId}")
    @SendTo("/topic/{recipeId}")
    public ChatMessage sendMessage(@DestinationVariable long recipeId, ChatMessage chatMessage) {
        System.out.println(chatMessage.getMessage());
        return chatMessage;
    }
}
