package org.koffa.recipefrontend.api;

import org.koffa.recipefrontend.pojo.ChatMessage;

import java.io.IOException;
import java.util.List;

public interface ChatMessageGetter {
    List<ChatMessage> getRecipeChatMessages(long recipeId) throws IOException;
}
