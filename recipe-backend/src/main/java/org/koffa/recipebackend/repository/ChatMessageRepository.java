package org.koffa.recipebackend.repository;

import org.koffa.recipebackend.entity.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    List<ChatMessage> findByRecipeId(long recipeId);
}
