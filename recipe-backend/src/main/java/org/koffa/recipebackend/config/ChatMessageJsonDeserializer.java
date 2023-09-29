package org.koffa.recipebackend.config;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.koffa.recipebackend.entity.Recipe;
import org.koffa.recipebackend.service.ChatMessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.koffa.recipebackend.entity.ChatMessage;

import java.io.IOException;

public class ChatMessageJsonDeserializer extends JsonDeserializer<ChatMessage> {
    Logger logger = LoggerFactory.getLogger(ChatMessageJsonDeserializer.class);
    public ChatMessageJsonDeserializer() {
        super();
        this.addTrustedPackages("org.koffa.recipebackend.entity");
        this.setRemoveTypeHeaders(false);
        this.setUseTypeMapperForKey(true);
    }
    @Override
    public ChatMessage deserialize(String topic, byte[] data) {
        ObjectMapper mapper = new ObjectMapper();
        logger.info("Deserializing chat message");
        try {
            ChatMessage chatMessage = mapper.readValue(data, ChatMessage.class);
            logger.info("Deserialized chat message: " + chatMessage);
            return mapper.readValue(data, ChatMessage.class);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }
    }
}
