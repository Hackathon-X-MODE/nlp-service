package com.example.example.controller;

import com.example.example.domain.NlpModel;
import com.example.example.service.nlp.NlpService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("nlp")
@RequiredArgsConstructor
public class NlpController {

    private final NlpService nlpService;

    @Operation(
            summary = "Сменить NLP модель"
    )
    @PutMapping("/model/{model}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void changeModel(@PathVariable("model") NlpModel nlpModel) {
        this.nlpService.changeActiveModel(nlpModel);
    }
}
