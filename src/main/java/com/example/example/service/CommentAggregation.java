package com.example.example.service;

import com.example.example.model.CommentAnalyst;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentAggregation {

    private final CommentNlpService commentNlpService;

    public CommentAnalyst analyst(String comment) {
        final var preparedComment = comment.strip();
        log.info("Analyse {}", preparedComment);

        return CommentAnalyst.builder()
                .mood(this.commentNlpService.mood(comment))
                .commentTypes(this.commentNlpService.analysis(preparedComment))
                .build();
    }

}
