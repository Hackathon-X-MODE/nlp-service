package com.example.example.controller;

import com.example.example.model.CommentAnalystRequest;
import com.example.example.service.CommentReceive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
@RequiredArgsConstructor
public class AmqpController {

    private final CommentReceive commentReceive;

    @RabbitListener(queues = "comments", concurrency = "3")
    public void processComment(final CommentAnalystRequest analystRequest) {
        log.info("Accepted comment");
        this.commentReceive.receiveComment(analystRequest);
        log.info("Comment submitted");
    }

}
