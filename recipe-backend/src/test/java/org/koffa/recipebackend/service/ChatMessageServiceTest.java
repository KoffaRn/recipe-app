package org.koffa.recipebackend.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.koffa.recipebackend.entity.ChatMessage;
import org.koffa.recipebackend.repository.ChatMessageRepository;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {ChatMessageService.class})
@ExtendWith(SpringExtension.class)
class ChatMessageServiceTest {
    @MockBean
    private ChatMessageRepository chatMessageRepository;

    @Autowired
    private ChatMessageService chatMessageService;

    /**
     * Method under test: {@link ChatMessageService#save(ChatMessage)}
     */
    @Test
    void testSave() {
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setId(1L);
        chatMessage.setMessage("Not all who wander are lost");
        chatMessage.setRecipeId(1L);
        chatMessage.setSender("Sender");
        chatMessage.setTimestamp(mock(Timestamp.class));
        when(chatMessageRepository.save(Mockito.<ChatMessage>any())).thenReturn(chatMessage);

        ChatMessage chatMessage2 = new ChatMessage();
        chatMessage2.setId(1L);
        chatMessage2.setMessage("Not all who wander are lost");
        chatMessage2.setRecipeId(1L);
        chatMessage2.setSender("Sender");
        chatMessage2.setTimestamp(mock(Timestamp.class));
        chatMessageService.save(chatMessage2);
        verify(chatMessageRepository).save(Mockito.<ChatMessage>any());
        assertEquals(1L, chatMessage2.getId());
        assertEquals("Sender", chatMessage2.getSender());
        assertEquals(1L, chatMessage2.getRecipeId().longValue());
        assertEquals("Not all who wander are lost", chatMessage2.getMessage());
    }

    /**
     * Method under test: {@link ChatMessageService#getByRecipeId(long)}
     */
    @Test
    void testGetByRecipeId() {
        ArrayList<ChatMessage> chatMessageList = new ArrayList<>();
        when(chatMessageRepository.findByRecipeId(anyLong())).thenReturn(chatMessageList);
        List<ChatMessage> actualByRecipeId = chatMessageService.getByRecipeId(1L);
        assertSame(chatMessageList, actualByRecipeId);
        assertTrue(actualByRecipeId.isEmpty());
        verify(chatMessageRepository).findByRecipeId(anyLong());
    }
}

