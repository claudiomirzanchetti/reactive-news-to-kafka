package com.example.reactivenews2kafka.story;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/api/stories")
@Api("Hacker News' Stories REST API")
@RequiredArgsConstructor
public class StoryController {
    private final StoryPublisher storyPublisher;

    @GetMapping("{numberOfStoriesToPublish}")
    @ApiOperation("Retrieves the number of stories provided by parameter and publish them on the Kafka output topic.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The top stories that has been published according to the quantity provided by parameter."),
            @ApiResponse(code = 400, message = "The number of stories to fetch is invalid.")
    })
    public ResponseEntity<Mono<List<StoryDto>>> findStories(@PathVariable int numberOfStoriesToPublish) {
        return ResponseEntity.ok(storyPublisher.publish(numberOfStoriesToPublish));
    }
}
