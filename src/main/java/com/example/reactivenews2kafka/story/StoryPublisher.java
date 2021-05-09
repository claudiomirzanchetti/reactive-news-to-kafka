package com.example.reactivenews2kafka.story;

import com.example.reactivenews2kafka.common.KafkaProducer;
import com.example.reactivenews2kafka.common.MessageDto;
import com.example.reactivenews2kafka.util.JsonParser;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StoryPublisher {
    private final KafkaProducer kafkaProducer;
    private final HackerNewsService hackerNewsService;
    private final JsonParser jsonParser;

    @Value("${kafka.topic.output}")
    private String hackerNewsOutput;

    public Mono<List<StoryDto>> publish(Integer numberOfStoriesRequested) {
        return hackerNewsService
                .findRandomStories(numberOfStoriesRequested)
                .flatMap(this::publishStories);
    }

    private Mono<List<StoryDto>> publishStories(List<StoryDto> stories) {
        stories.stream()
                .forEach(story -> {
                    MessageDto message = MessageDto.builder()
                            .payload(jsonParser.writeToJson(story).get())
                            .build();
                    sendMessage(message);
                });
        return Mono.just(stories);
    }

    private void sendMessage(MessageDto messageDto) {
        kafkaProducer.sendMessage(messageDto, hackerNewsOutput, UUID.randomUUID().toString());
    }
}
