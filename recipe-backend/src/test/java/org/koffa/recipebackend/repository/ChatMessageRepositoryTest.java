package org.koffa.recipebackend.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;

import org.junit.jupiter.api.Test;
import org.koffa.recipebackend.entity.ChatMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration(classes = {ChatMessageRepository.class})
@EnableAutoConfiguration
@EntityScan(basePackages = {"org.koffa.recipebackend.entity"})
@DataJpaTest(properties = {"spring.main.allow-bean-definition-overriding=true"})
class ChatMessageRepositoryTest {
    @Autowired
    private ChatMessageRepository chatMessageRepository;

    /**
     * Method under test: {@link ChatMessageRepository#findByRecipeId(long)}
     */
    @Test
    void testFindByRecipeId() {
        Timestamp timestamp = mock(Timestamp.class);
        when(timestamp.getNanos()).thenReturn(1);
        when(timestamp.getTime()).thenReturn(10L);

        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setMessage("Not all who wander are lost");
        chatMessage.setRecipeId(1L);
        chatMessage.setSender("Sender");
        chatMessage.setTimestamp(timestamp);
        Timestamp timestamp2 = mock(Timestamp.class);
        when(timestamp2.getNanos()).thenReturn(1);
        when(timestamp2.getTime()).thenReturn(10L);

        ChatMessage chatMessage2 = new ChatMessage();
        chatMessage2.setMessage("Message");
        chatMessage2.setRecipeId(2L);
        chatMessage2.setSender("org.koffa.recipebackend.entity.ChatMessage");
        chatMessage2.setTimestamp(timestamp2);
        chatMessageRepository.save(chatMessage);
        chatMessageRepository.save(chatMessage2);
        assertEquals(1, chatMessageRepository.findByRecipeId(1L).size());
        verify(timestamp, atLeast(1)).getNanos();
        verify(timestamp, atLeast(1)).getTime();
        verify(timestamp2, atLeast(1)).getNanos();
        verify(timestamp2, atLeast(1)).getTime();
    }
}

