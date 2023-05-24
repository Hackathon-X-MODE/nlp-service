package com.example.example.service.comment;

import com.example.example.domain.NlpModel;
import com.example.example.model.CommentMood;
import com.example.example.model.CommentType;

import java.math.BigDecimal;
import java.util.List;

public interface CommentNlpService {

    List<CommentType> analysis(String comment, BigDecimal temperature);

    CommentMood mood(String comment, BigDecimal temperature);


    NlpModel getModel();
}
