package org.koffa.recipebackend.repository;

import jakarta.transaction.Transactional;
import org.koffa.recipebackend.entity.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    List<ChatMessage> findByRecipeId(long recipeId);
    @Transactional
    void deleteByRecipeId(long id);
}