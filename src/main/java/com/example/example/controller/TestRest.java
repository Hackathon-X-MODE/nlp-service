package com.example.example.controller;

import com.example.example.model.CommentAnalyst;
import com.example.example.model.CommentAnalystRequest;
import com.example.example.service.CommentAggregation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("comment")
@RequiredArgsConstructor
public class TestRest {

    private final CommentAggregation commentAggregation;


    @PostMapping
    public CommentAnalyst test(@RequestBody String data) {
        return this.commentAggregation.analyst(
                CommentAnalystRequest.builder()
                        .comment(data)
                        .needTypes(true)
                        .build()
        );
    }
}
