package org.koffa.recipebackend.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.koffa.recipebackend.entity.ChatMessage;
import org.koffa.recipebackend.entity.Message;
import org.koffa.recipebackend.kafka.KafkaProducer;
import org.koffa.recipebackend.service.ChatMessageService;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {ChatController.class})
@ExtendWith(SpringExtension.class)
class ChatControllerTest {
    @Autowired
    private ChatController chatController;

    @MockBean
    private ChatMessageService chatMessageService;

    @MockBean
    private KafkaProducer kafkaProducer;

    /**
     * Method under test: {@link ChatController#chatByRecipe(long)}
     */
    @Test
    void testChatByRecipe() throws Exception {
        when(chatMessageService.getByRecipeId(anyLong())).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/chat/get/{recipeId}", 1L);
        MockMvcBuilders.standaloneSetup(chatController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    /**
     * Method under test: {@link ChatController#sendMessage(long, Message)}
     */
    @Test
    void testSendMessage() {
        doNothing().when(kafkaProducer).sendMessage(Mockito.<ChatMessage>any());

        Message message = new Message();
        message.setMessage("Not all who wander are lost");
        message.setSender("Sender");
        ChatMessage actualSendMessageResult = chatController.sendMessage(1L, message);
        assertEquals("Sender", actualSendMessageResult.getSender());
        assertEquals(1L, actualSendMessageResult.getRecipeId().longValue());
        assertEquals("Not all who wander are lost", actualSendMessageResult.getMessage());
        verify(kafkaProducer).sendMessage(Mockito.<ChatMessage>any());
    }
}

