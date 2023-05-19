package com.example.example.controller;

import com.example.example.service.CommentNlpService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("comment")
@RequiredArgsConstructor
public class TestRest {

    private final CommentNlpService commentNlpService;


    @PostMapping
    Map<String, Object> test(@RequestBody String data) {
        return Map.of(
                "criteria", this.commentNlpService.analysis(data),
                "mood", this.commentNlpService.mood(data)
        );
    }
}
