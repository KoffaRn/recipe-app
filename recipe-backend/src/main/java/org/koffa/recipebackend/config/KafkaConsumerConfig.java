package org.koffa.recipebackend.config;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.koffa.recipebackend.entity.ChatMessage;
import org.koffa.recipebackend.entity.Recipe;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
public class KafkaConsumerConfig {
    @Value(value = "${spring.kafka.bootstrap-servers}")
    private String bootstrapAddress;
    @Value(value = "${spring.kafka.recipe.group-id}")
    private String groupId;
    @Value(value = "${spring.kafka.chat.group-id}")
    private String chatGroupId;

    /**
     * Creates a consumer factory for the Recipe entity.
     * @return
     */
    @Bean
    public ConsumerFactory<String, Recipe> consumerFactory() {
        Map<String, Object> props = getCommonProps(groupId);
        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), new ErrorHandlingDeserializer<>(new RecipeJsonDeseriazlier()));
    }

    /**
     * Creates a consumer factory for the ChatMessage entity.
     * @return
     */
    @Bean
    public ConsumerFactory<String, ChatMessage> chatConsumerFactory() {
        getCommonProps(chatGroupId);
        Map<String, Object> props = getCommonProps(chatGroupId);
        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), new ErrorHandlingDeserializer<>(new ChatMessageJsonDeserializer()));
    }
    // Common properties for both consumer factories

    private Map<String, Object> getCommonProps(String groupId) {
        Map<String, Object> props = new HashMap<>();
        props.put(
                ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
                bootstrapAddress);
        props.put(
                ConsumerConfig.GROUP_ID_CONFIG,
                groupId);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
                StringDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
                ErrorHandlingDeserializer.class.getName());
        return props;
    }

    /**
     * Creates a Kafka listener container factory for the Recipe entity.
     * @return Kafka listener container factory for the Recipe entity.
     */

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Recipe>
    kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, Recipe> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }

    /**
     * Creates a Kafka listener container factory for the ChatMessage entity.
     * @return Kafka listener container factory for the ChatMessage entity.
     */
    @Bean ConcurrentKafkaListenerContainerFactory<String, ChatMessage> kafkaChatListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, ChatMessage> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(chatConsumerFactory());
        return factory;
    }
}