package com.example.example.client;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Component
public class ChatGpt {

    private final WebClient webClient;

    public ChatGpt(@Value("${gpt.key}") String key) {
        this.webClient = WebClient.builder()
                .baseUrl("https://api.openai.com/v1")
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + key)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    public String message(String messages) {
        log.info("Try {}", messages);
        final var result = this.webClient.post()
                .uri(uriBuilder -> uriBuilder.path("/chat/completions").build())
                .bodyValue(
                        Map.of(
                                "model", "gpt-3.5-turbo-0301",
                                "messages", List.of(
                                        Map.of("role", "user",
                                                "content", messages
                                        )
                                ),
                                "temperature", 0
                        )
                ).retrieve()
                .bodyToMono(Response.class)
                .block();
        log.info("Got {}", result);

        return result.choices.stream()
                .map(ResponsesChoices::getMessage)
                .map(ResponsesChoicesMessage::getContent)
                .collect(Collectors.joining(" "));
    }


    @Data
    static class Response {
        private List<ResponsesChoices> choices;
    }

    @Data
    static class ResponsesChoices {
        private ResponsesChoicesMessage message;
    }

    @Data
    static class ResponsesChoicesMessage {
        private String content;
    }
}
