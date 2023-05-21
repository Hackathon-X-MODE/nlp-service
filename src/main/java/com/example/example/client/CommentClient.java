package com.example.example.client;

import com.example.example.model.CommentAnalyst;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Component
public class CommentClient {


    private final WebClient webClient;

    public CommentClient() {
        this.webClient = WebClient.builder()
                .baseUrl("http://comment-service:" + Optional.ofNullable(System.getenv("SERVER_HTTP_PORT")).orElse("8083"))
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }


    public void update(CommentAnalyst commentAnalyst) {
        this.webClient.patch()
                .uri(uriBuilder -> uriBuilder.path("/external/{commentId}").build(commentAnalyst.getCommentId()))
                .bodyValue(Map.of(
                        "mood", commentAnalyst.getMood(),
                        "commentTypes", commentAnalyst.getCommentTypes()
                ))
                .retrieve()
                .toBodilessEntity()
                .block();
    }
}
