package com.example.example.service;

import com.example.example.client.CommentClient;
import com.example.example.model.CommentAnalystRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentReceive {

    private final CommentAggregation commentAggregation;

    private final CommentClient commentClient;

    public void receiveComment(CommentAnalystRequest commentAnalystRequest) {
        this.commentClient.update(this.commentAggregation.analyst(commentAnalystRequest));
    }
}
