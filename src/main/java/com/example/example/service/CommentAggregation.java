package com.example.example.service;

import com.example.example.client.CommentClient;
import com.example.example.model.CommentAnalyst;
import com.example.example.model.CommentAnalystRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentAggregation {

    private final CommentNlpService commentNlpService;

    public CommentAnalyst analyst(CommentAnalystRequest analystRequest) {
        final var preparedComment = analystRequest.getComment().strip();
        log.info("Analyse {}", preparedComment);

        return CommentAnalyst.builder()
                .commentId(analystRequest.getId())
                .mood(this.commentNlpService.mood(preparedComment))
                .commentTypes(analystRequest.isNeedTypes() ? this.commentNlpService.analysis(preparedComment) : null)
                .build();
    }

}
