package com.example.reactivenews2kafka.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Component
@AllArgsConstructor
@Slf4j
public class JsonParser {
    private final ObjectMapper objectMapper;

    public <T> Optional<T> readFromJson(String payload, Class<T> valueType) {
        try {
            return Optional.ofNullable(objectMapper.readValue(payload, valueType));
        } catch (Exception e) {
            log.error("An error has occurred while reading from JSON: payload={}", payload, e);
        }

        return Optional.empty();
    }

    public <T> Optional<String> writeToJson(T object) {
        try {
            return Optional.ofNullable(objectMapper.writeValueAsString(object));
        } catch (Exception e) {
            log.error("An error has occurred while converting the object to JSON, object={}", object, e);
        }

        return Optional.empty();
    }
}
