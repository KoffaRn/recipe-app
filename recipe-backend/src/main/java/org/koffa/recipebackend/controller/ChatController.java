package org.koffa.recipebackend.controller;

import org.koffa.recipebackend.entity.ChatMessage;
import org.koffa.recipebackend.entity.Message;
import org.koffa.recipebackend.kafka.KafkaProducer;
import org.koffa.recipebackend.service.ChatMessageService;
import org.koffa.recipebackend.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

@Controller
@RequestMapping("api/v1/chat")
public class ChatController {
    private KafkaProducer kafkaProducer;
    private ChatMessageService chatMessageService;
    @Autowired
    public ChatController(KafkaProducer kafkaProducer, ChatMessageService chatMessageService) {
        this.kafkaProducer = kafkaProducer;
        this.chatMessageService = chatMessageService;
    }
    @MessageMapping("/chat/{recipeId}")
    @SendTo("/topic/{recipeId}")
    public ChatMessage sendMessage(@DestinationVariable long recipeId, Message message) {
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setSender(message.getSender());
        chatMessage.setMessage(message.getMessage());
        chatMessage.setRecipeId(recipeId);
        chatMessage.setTimestamp(Timestamp.from(Instant.now()));
        kafkaProducer.sendMessage(chatMessage);
        return chatMessage;
    }
    @GetMapping("/get/{recipeId}")
    public ResponseEntity<List<ChatMessage>> chatByRecipe(@PathVariable long recipeId) {
        try {
            return ResponseEntity.ok().body(chatMessageService.getByRecipeId(recipeId));
        }
        catch (Exception e) {
            return ResponseEntity.internalServerError().body(null);
        }
    }
}
