package com.example.example.service;

import com.example.example.exception.EntityNotFoundException;
import com.example.example.model.CommentAnalyst;
import com.example.example.model.CommentAnalystRequest;
import com.example.example.service.comment.CommentNlpService;
import com.example.example.service.nlp.NlpService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentAggregation {


    private final NlpService nlpService;

    private final List<CommentNlpService> commentNlpServices;

    public CommentAnalyst analyst(CommentAnalystRequest analystRequest) {
        final var preparedComment = analystRequest.getComment().strip();
        final var activeModel = this.nlpService.getActiveModel();
        log.info("Analyse {} via {}({})", preparedComment, activeModel.getModel(), activeModel.getTemperature());

        return this.commentNlpServices.stream().filter(
                        commentNlpService -> activeModel.getModel().equals(commentNlpService.getModel())
                ).findFirst()
                .map(commentNlpService ->
                        CommentAnalyst.builder()
                                .commentId(analystRequest.getId())
                                .mood(commentNlpService.mood(preparedComment, activeModel.getTemperature()))
                                .commentTypes(analystRequest.isNeedTypes() ? commentNlpService.analysis(preparedComment, activeModel.getTemperature()) : null)
                                .build()
                )
                .orElseThrow(
                        () -> new EntityNotFoundException("Can't find active model " + activeModel.getModel())
                );
    }

}
