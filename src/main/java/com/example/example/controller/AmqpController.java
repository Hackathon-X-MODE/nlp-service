package com.example.example.controller;

import com.example.example.model.CommentAnalystRequest;
import com.example.example.service.CommentAggregation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
@RequiredArgsConstructor
public class AmqpController {

    private final CommentAggregation commentAggregation;

    @RabbitListener(queues = "comments")
    public void processComment(final CommentAnalystRequest analystRequest) {
        log.info("Accepted comment");
        final var result = this.commentAggregation.analyst(analystRequest);
        log.info("Comment submitted");
    }

}
