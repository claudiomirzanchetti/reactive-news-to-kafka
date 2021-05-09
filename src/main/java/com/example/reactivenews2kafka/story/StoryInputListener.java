package com.example.reactivenews2kafka.story;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class StoryInputListener {
    private final StoryPublisher storyPublisher;

    @KafkaListener(topics = "hacker-news.input", groupId = "hacker-news")
    public void listenInput(ConsumerRecord<Object, Object> record, @Payload Integer payload, Acknowledgment acknowledgment) {
        log.info("A demand of {} stories to publish has been received.", payload);

        storyPublisher.publish(payload).block();

        acknowledgment.acknowledge();
    }
}
