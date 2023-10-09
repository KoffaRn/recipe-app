package org.koffa.recipebackend.service;

import org.koffa.recipebackend.entity.ChatMessage;
import org.koffa.recipebackend.repository.ChatMessageRepository;
import org.springframework.stereotype.Service;
import java.util.Collections;
import java.util.List;

@Service
public class ChatMessageService {
    private final ChatMessageRepository chatMessageRepository;
    public ChatMessageService(ChatMessageRepository chatMessageRepository) {
        this.chatMessageRepository = chatMessageRepository;
    }
    public void save(ChatMessage chatMessage) {
        chatMessageRepository.save(chatMessage);
    }
    public List<ChatMessage> getByRecipeId(long recipeId) {
        List<ChatMessage> messages = chatMessageRepository.findByRecipeId(recipeId);
        Collections.sort(messages);
        return messages;
    }

    public void deleteByRecipeId(long id) {
        chatMessageRepository.deleteByRecipeId(id);
    }
}
