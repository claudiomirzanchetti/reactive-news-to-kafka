package com.example.reactivenews2kafka.story;

import com.example.reactivenews2kafka.exception.InvalidArgumentException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Getter
public class HackerNewsService {
    private static final String JSON_SUFFIX = ".json";

    @Qualifier("hackerNewsApiWebClient")
    private final WebClient hackerNewsApiWebClient;

    @Value("${hackerNews.url.topStories}")
    private String topStoriesUrl;

    @Value("${hackerNews.url.story}")
    private String baseStoryUrl;

    public Mono<List<StoryDto>> findRandomStories(int numberToFetch) {
        return findTopStoryIds()
                .flatMap(topStoryIds -> validateNumberOfStories(numberToFetch, topStoryIds))
                .flatMap(topStoryIds -> findRandomStoryIds(numberToFetch, topStoryIds))
                .flatMap(storyIds -> findRandomStories(storyIds));
    }

    private Mono<List<Integer>> findRandomStoryIds(int limit, Integer[] storyIds) {
        return Mono.just(new Random()
                .ints(limit, 0, storyIds.length)
                .boxed()
                .collect(Collectors.toList()));
    }

    private Mono<List<StoryDto>> findRandomStories(List<Integer> storyIds) {
        return Flux
                .fromIterable(storyIds)
                .flatMap(id -> findStory(id))
                .collectList();
    }

    private Mono<Integer[]> findTopStoryIds() {
        return hackerNewsApiWebClient
                .get()
                .uri(topStoriesUrl)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(Integer[].class);
    }

    private Mono<StoryDto> findStory(Integer id) {
        String storyUrl = StringUtils.join(baseStoryUrl, id, JSON_SUFFIX);
        return hackerNewsApiWebClient
                .get()
                .uri(storyUrl)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(StoryDto.class);
    }

    private Mono<Integer[]> validateNumberOfStories(int numberOfStoriesToPublish, Integer[] topStoriesIds) {
        if (numberOfStoriesToPublish < 1 || numberOfStoriesToPublish > topStoriesIds.length) {
            throw new InvalidArgumentException(StringUtils.join("The number of stories to publish needs to between 1 and ", topStoriesIds.length));
        }

        return Mono.just(topStoriesIds);
    }
}
