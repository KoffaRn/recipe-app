package org.koffa.recipebackend.kafka;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.TopicPartition;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.koffa.recipebackend.entity.ChatMessage;
import org.koffa.recipebackend.entity.Ingredient;
import org.koffa.recipebackend.entity.Recipe;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {KafkaProducer.class})
@ExtendWith(SpringExtension.class)
class KafkaProducerTest {
    @Autowired
    private KafkaProducer kafkaProducer;

    @MockBean
    private KafkaTemplate<String, Recipe> kafkaTemplate;

    @MockBean
    private KafkaTemplate<String, ChatMessage> kafkaTemplate2;

    /**
     * Method under test: {@link KafkaProducer#sendMessage(ChatMessage)}
     */
    @Test
    void testSendMessage() {
        when(kafkaTemplate2.send(Mockito.any(), Mockito.any())).thenReturn(new CompletableFuture<>());

        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setId(1L);
        chatMessage.setMessage("Not all who wander are lost");
        chatMessage.setRecipeId(1L);
        chatMessage.setSender("Sender");
        chatMessage.setTimestamp(mock(Timestamp.class));
        kafkaProducer.sendMessage(chatMessage);
        verify(kafkaTemplate2).send(Mockito.any(), Mockito.any());
        assertEquals(1L, chatMessage.getId());
        assertEquals("Sender", chatMessage.getSender());
        assertEquals(1L, chatMessage.getRecipeId().longValue());
        assertEquals("Not all who wander are lost", chatMessage.getMessage());
    }

    /**
     * Method under test: {@link KafkaProducer#sendMessage(ChatMessage)}
     */
    @Test
    void testSendMessage2() {
        CompletableFuture<SendResult<String, ChatMessage>> completableFuture = new CompletableFuture<>();
        ProducerRecord<String, ChatMessage> producerRecord = new ProducerRecord<>("Topic", new ChatMessage());

        completableFuture.obtrudeValue(
                new SendResult<>(producerRecord, new RecordMetadata(new TopicPartition("Topic", 1), 1L, 1, 10L, 3, 3)));
        when(kafkaTemplate2.send(Mockito.any(), Mockito.any())).thenReturn(completableFuture);

        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setId(1L);
        chatMessage.setMessage("Not all who wander are lost");
        chatMessage.setRecipeId(1L);
        chatMessage.setSender("Sender");
        chatMessage.setTimestamp(mock(Timestamp.class));
        kafkaProducer.sendMessage(chatMessage);
        verify(kafkaTemplate2).send(Mockito.any(), Mockito.any());
        assertEquals(1L, chatMessage.getId());
        assertEquals("Sender", chatMessage.getSender());
        assertEquals(1L, chatMessage.getRecipeId().longValue());
        assertEquals("Not all who wander are lost", chatMessage.getMessage());
    }

    /**
     * Method under test: {@link KafkaProducer#sendMessage(ChatMessage)}
     */
    @Test
    void testSendMessage3() {
        CompletableFuture<SendResult<String, ChatMessage>> completableFuture = new CompletableFuture<>();
        completableFuture.obtrudeException(new Throwable());
        when(kafkaTemplate2.send(Mockito.any(), Mockito.any())).thenReturn(completableFuture);

        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setId(1L);
        chatMessage.setMessage("Not all who wander are lost");
        chatMessage.setRecipeId(1L);
        chatMessage.setSender("Sender");
        chatMessage.setTimestamp(mock(Timestamp.class));
        kafkaProducer.sendMessage(chatMessage);
        verify(kafkaTemplate2).send(Mockito.any(), Mockito.any());
        assertEquals(1L, chatMessage.getId());
        assertEquals("Sender", chatMessage.getSender());
        assertEquals(1L, chatMessage.getRecipeId().longValue());
        assertEquals("Not all who wander are lost", chatMessage.getMessage());
    }

    /**
     * Method under test: {@link KafkaProducer#sendMessage(ChatMessage)}
     */
    @Test
    void testSendMessage4() {
        CompletableFuture<SendResult<String, ChatMessage>> completableFuture = new CompletableFuture<>();
        completableFuture.obtrudeValue(new SendResult<>(new ProducerRecord<>("Topic", new ChatMessage()), null));
        when(kafkaTemplate2.send(Mockito.any(), Mockito.any())).thenReturn(completableFuture);

        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setId(1L);
        chatMessage.setMessage("Not all who wander are lost");
        chatMessage.setRecipeId(1L);
        chatMessage.setSender("Sender");
        chatMessage.setTimestamp(mock(Timestamp.class));
        kafkaProducer.sendMessage(chatMessage);
        verify(kafkaTemplate2).send(Mockito.any(), Mockito.any());
        assertEquals(1L, chatMessage.getId());
        assertEquals("Sender", chatMessage.getSender());
        assertEquals(1L, chatMessage.getRecipeId().longValue());
        assertEquals("Not all who wander are lost", chatMessage.getMessage());
    }

    /**
     * Method under test: {@link KafkaProducer#sendMessage(ChatMessage)}
     */
    @Test
    void testSendMessage5() {
        CompletableFuture<SendResult<String, ChatMessage>> completableFuture = new CompletableFuture<>();
        completableFuture.obtrudeValue(null);
        when(kafkaTemplate2.send(Mockito.any(), Mockito.any())).thenReturn(completableFuture);

        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setId(1L);
        chatMessage.setMessage("Not all who wander are lost");
        chatMessage.setRecipeId(1L);
        chatMessage.setSender("Sender");
        chatMessage.setTimestamp(mock(Timestamp.class));
        kafkaProducer.sendMessage(chatMessage);
        verify(kafkaTemplate2).send(Mockito.any(), Mockito.any());
        assertEquals(1L, chatMessage.getId());
        assertEquals("Sender", chatMessage.getSender());
        assertEquals(1L, chatMessage.getRecipeId().longValue());
        assertEquals("Not all who wander are lost", chatMessage.getMessage());
    }

    /**
     * Method under test: {@link KafkaProducer#sendMessage(Recipe)}
     */
    @Test
    void testSendMessage6() {
        when(kafkaTemplate.send(Mockito.any(), Mockito.any())).thenReturn(new CompletableFuture<>());

        Recipe message = new Recipe();
        message.setDescription("The characteristics of someone or something");
        message.setId(1L);
        ArrayList<Ingredient> ingredients = new ArrayList<>();
        message.setIngredients(ingredients);
        message.setName("Name");
        message.setSteps(new ArrayList<>());
        message.setTags(new ArrayList<>());
        kafkaProducer.sendMessage(message);
        verify(kafkaTemplate).send(Mockito.any(), Mockito.any());
        assertEquals("The characteristics of someone or something", message.getDescription());
        assertEquals(ingredients, message.getTags());
        assertEquals(ingredients, message.getSteps());
        assertEquals("Name", message.getName());
        assertTrue(message.getIngredients().isEmpty());
        assertEquals(1L, message.getId().longValue());
    }

    /**
     * Method under test: {@link KafkaProducer#sendMessage(Recipe)}
     */
    @Test
    void testSendMessage7() {
        CompletableFuture<SendResult<String, Recipe>> completableFuture = new CompletableFuture<>();
        ProducerRecord<String, Recipe> producerRecord = new ProducerRecord<>("Topic", new Recipe());

        completableFuture.obtrudeValue(
                new SendResult<>(producerRecord, new RecordMetadata(new TopicPartition("Topic", 1), 1L, 1, 10L, 3, 3)));
        when(kafkaTemplate.send(Mockito.any(), Mockito.any())).thenReturn(completableFuture);

        Recipe message = new Recipe();
        message.setDescription("The characteristics of someone or something");
        message.setId(1L);
        ArrayList<Ingredient> ingredients = new ArrayList<>();
        message.setIngredients(ingredients);
        message.setName("Name");
        message.setSteps(new ArrayList<>());
        message.setTags(new ArrayList<>());
        kafkaProducer.sendMessage(message);
        verify(kafkaTemplate).send(Mockito.any(), Mockito.any());
        assertEquals("The characteristics of someone or something", message.getDescription());
        assertEquals(ingredients, message.getTags());
        assertEquals(ingredients, message.getSteps());
        assertEquals("Name", message.getName());
        assertTrue(message.getIngredients().isEmpty());
        assertEquals(1L, message.getId().longValue());
    }

    /**
     * Method under test: {@link KafkaProducer#sendMessage(Recipe)}
     */
    @Test
    void testSendMessage8() {
        CompletableFuture<SendResult<String, Recipe>> completableFuture = new CompletableFuture<>();
        completableFuture.obtrudeException(new Throwable());
        when(kafkaTemplate.send(Mockito.any(), Mockito.any())).thenReturn(completableFuture);

        Recipe message = new Recipe();
        message.setDescription("The characteristics of someone or something");
        message.setId(1L);
        ArrayList<Ingredient> ingredients = new ArrayList<>();
        message.setIngredients(ingredients);
        message.setName("Name");
        message.setSteps(new ArrayList<>());
        message.setTags(new ArrayList<>());
        kafkaProducer.sendMessage(message);
        verify(kafkaTemplate).send(Mockito.any(), Mockito.any());
        assertEquals("The characteristics of someone or something", message.getDescription());
        assertEquals(ingredients, message.getTags());
        assertEquals(ingredients, message.getSteps());
        assertEquals("Name", message.getName());
        assertTrue(message.getIngredients().isEmpty());
        assertEquals(1L, message.getId().longValue());
    }

    /**
     * Method under test: {@link KafkaProducer#sendMessage(Recipe)}
     */
    @Test
    void testSendMessage9() {
        CompletableFuture<SendResult<String, Recipe>> completableFuture = new CompletableFuture<>();
        completableFuture.obtrudeValue(new SendResult<>(new ProducerRecord<>("Topic", new Recipe()), null));
        when(kafkaTemplate.send(Mockito.any(), Mockito.any())).thenReturn(completableFuture);

        Recipe message = new Recipe();
        message.setDescription("The characteristics of someone or something");
        message.setId(1L);
        ArrayList<Ingredient> ingredients = new ArrayList<>();
        message.setIngredients(ingredients);
        message.setName("Name");
        message.setSteps(new ArrayList<>());
        message.setTags(new ArrayList<>());
        kafkaProducer.sendMessage(message);
        verify(kafkaTemplate).send(Mockito.any(), Mockito.any());
        assertEquals("The characteristics of someone or something", message.getDescription());
        assertEquals(ingredients, message.getTags());
        assertEquals(ingredients, message.getSteps());
        assertEquals("Name", message.getName());
        assertTrue(message.getIngredients().isEmpty());
        assertEquals(1L, message.getId().longValue());
    }

    /**
     * Method under test: {@link KafkaProducer#sendMessage(Recipe)}
     */
    @Test
    void testSendMessage10() {
        CompletableFuture<SendResult<String, Recipe>> completableFuture = new CompletableFuture<>();
        completableFuture.obtrudeValue(null);
        when(kafkaTemplate.send(Mockito.any(), Mockito.any())).thenReturn(completableFuture);

        Recipe message = new Recipe();
        message.setDescription("The characteristics of someone or something");
        message.setId(1L);
        ArrayList<Ingredient> ingredients = new ArrayList<>();
        message.setIngredients(ingredients);
        message.setName("Name");
        message.setSteps(new ArrayList<>());
        message.setTags(new ArrayList<>());
        kafkaProducer.sendMessage(message);
        verify(kafkaTemplate).send(Mockito.any(), Mockito.any());
        assertEquals("The characteristics of someone or something", message.getDescription());
        assertEquals(ingredients, message.getTags());
        assertEquals(ingredients, message.getSteps());
        assertEquals("Name", message.getName());
        assertTrue(message.getIngredients().isEmpty());
        assertEquals(1L, message.getId().longValue());
    }
}

