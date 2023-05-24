package com.example.example.client;

import com.example.example.model.CommentMood;
import com.example.example.model.CommentType;
import com.example.example.model.spacy.SpacyModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class SpacyClient {

    private final WebClient webClient;

    public SpacyClient() {
        this.webClient = WebClient.builder()
                .baseUrl("http://spacy-nlp-service:5000")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }


    public Map<SpacyModel, Map<CommentType, BigDecimal>> getTypes(String comment) {
        return this.webClient.post()
                .uri("/types")
                .bodyValue(comment)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<CommentTypeResult>>() {
                })
                .block()
                .stream().collect(
                        Collectors.toMap(commentTypeResult ->
                                        SpacyModel.valueOf(commentTypeResult.getName()),
                                CommentTypeResult::getResult
                        )
                );

    }

    public Map<CommentMood, BigDecimal> getMood(String comment) {
        return this.webClient.post()
                .uri("/mood")
                .bodyValue(comment)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<CommentMood, BigDecimal>>() {
                })
                .block();
    }


    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CommentTypeResult {
        private String name;

        private Map<CommentType, BigDecimal> result;
    }
}
