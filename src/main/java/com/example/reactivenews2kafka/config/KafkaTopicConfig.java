package com.example.reactivenews2kafka.config;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaTopicConfig {
    @Value("${kafka.bootstrapServer}")
    private String bootstrapServer;

    @Value("${kafka.topic.input}")
    private String hackerNewsInput;

    @Value("${kafka.topic.output}")
    private String hackerNewsOutput;

    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
        return new KafkaAdmin(configs);
    }

    @Bean
    public NewTopic hackerNewsInput() {
        return new NewTopic(hackerNewsInput, 1, (short) 1);
    }

    @Bean
    public NewTopic hackerNewsOutput() {
        return new NewTopic(hackerNewsOutput, 1, (short) 1);
    }
}
