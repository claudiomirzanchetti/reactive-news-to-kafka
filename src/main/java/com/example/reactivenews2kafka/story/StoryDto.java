package com.example.reactivenews2kafka.story;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class StoryDto {
    private Long id;

    @JsonProperty("by")
    private String author;

    private int score;

    private String title;

    private String url;
}
