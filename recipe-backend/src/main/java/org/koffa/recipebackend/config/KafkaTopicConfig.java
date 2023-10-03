package org.koffa.recipebackend.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {
    @Value(value = "${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;
    @Value(value = "${spring.kafka.recipe.topic-name}")
    private String topic;
    @Value(value = "${spring.kafka.chat.topic-name}")
    private String topicChat;

    /**
     * Creates a topic for the Recipe entity.
     * @return NewTopic
     */
    @Bean
    public NewTopic createTopic() {
        return TopicBuilder.name(topic)
                .build();
    }

    /**
     * Creates a topic for the ChatMessage entity.
     * @return NewTopic
     */
    @Bean public NewTopic createTopicChat() {
        return TopicBuilder.name(topicChat)
                .build();
    }
}
