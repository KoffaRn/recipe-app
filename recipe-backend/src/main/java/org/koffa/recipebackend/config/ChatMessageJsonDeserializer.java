package org.koffa.recipebackend.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.koffa.recipebackend.entity.ChatMessage;

public class ChatMessageJsonDeserializer extends JsonDeserializer<ChatMessage> {
    private final Logger logger = LoggerFactory.getLogger(ChatMessageJsonDeserializer.class);

    /**
     * Deserializes a JSON string into a ChatMessage object.
     */
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
