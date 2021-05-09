package com.example.reactivenews2kafka.common;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.kafka.support.SendResult;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import static org.springframework.messaging.MessageHeaders.CONTENT_TYPE;

@Component
@RequiredArgsConstructor
@Slf4j
public class KafkaProducer {
    private static final String APPLICATION_JSON = "application/json";

    private final KafkaTemplate<String, MessageDto> kafkaTemplate;

    public <T> T sendMessage(T payload, String topic, String key) {
        Message<T> message = MessageBuilder
                .withPayload(payload)
                .setHeader(KafkaHeaders.TOPIC, topic)
                .setHeader(KafkaHeaders.MESSAGE_KEY, key)
                .setHeader(CONTENT_TYPE, APPLICATION_JSON)
                .build();

        ListenableFuture<SendResult<String, MessageDto>> futureResult = kafkaTemplate.send(message);

        futureResult.addCallback(new ListenableFutureCallback<SendResult<String, MessageDto>>() {
            @Override
            public void onSuccess(SendResult<String, MessageDto> result) {
                log.info("Message=[" + payload + "] has been sent."
                        + " Topic: " + result.getRecordMetadata().topic()
                        + " Partition: " + result.getRecordMetadata().partition()
                        + " Offset: " + result.getRecordMetadata().offset());
            }

            @Override
            public void onFailure(Throwable ex) {
                log.info("Unable to send message=[" + payload + "] due to : " + ex.getMessage());
            }
        });

        return payload;
    }
}
