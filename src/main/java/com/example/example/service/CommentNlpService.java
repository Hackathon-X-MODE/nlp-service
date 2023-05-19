package com.example.example.service;

import com.example.example.client.ChatGpt;
import com.example.example.model.CommentMood;
import com.example.example.model.CommentType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Slf4j
@Service
public class CommentNlpService {

    private static final String TEMPLATE_CRITERIA = """
            Распознай тематику отзыва о доставке товара через постамат из категорий: %_criteria_%. Через запятую
            Сообщение: "%_comment_%"
            """;

    private static final String TEMPLATE_MOOD = """
            Позитивный ли окрас у сообщения "%_comment_%" Ответь одним словом
            """;
    private static final Map<String, CommentType> MAP_NLP_TO_TYPE = Map.of(
            "описание товара на сайте интернет-магазина", CommentType.PRODUCT_DESCRIPTION,
            "оформление заказа", CommentType.PREPARE_ORDER,
            "получение заказа", CommentType.GETTING_ORDER,
            "полученный заказ", CommentType.GOT_ORDER,
            "товар", CommentType.PRODUCT,
            "работа постамата", CommentType.POST_BOX,
            "доставка", CommentType.DELIVERY,
            "уведомления", CommentType.NOTIFICATION,
            "иное", CommentType.OTHER
    );

    private static final Map<CommentMood, Set<String>> MAP_MOOD_TO_NLP = Map.of(
            CommentMood.POSITIVE, Set.of("да", "позитив"),
            CommentMood.NEGATIVE, Set.of("нет", "негатив")
    );

    private final String template;
    private final ChatGpt chatGpt;

    public CommentNlpService(ChatGpt chatGpt) {
        this.chatGpt = chatGpt;
        this.template = TEMPLATE_CRITERIA.replace("%_criteria_%", "\"" + String.join("\",\"", MAP_NLP_TO_TYPE.keySet()) + "\"");
        log.info("Generated template: {}", this.template);
    }


    public List<CommentType> analysis(String comment) {
        log.info("Calculate message {}", comment.strip());
        final var message = this.chatGpt.message(this.template.replace("%_comment_%", comment.strip())).toLowerCase();
        final var result = MAP_NLP_TO_TYPE.keySet().stream().filter(message::contains).map(MAP_NLP_TO_TYPE::get).toList();
        log.info("Found {}", result);
        return result;
    }

    public CommentMood mood(String comment) {
        final var message = this.chatGpt.message(TEMPLATE_MOOD.replace("%_comment_%", comment.strip())).toLowerCase();

        final var result = MAP_MOOD_TO_NLP.keySet().stream().filter(key -> {
                    final var nlpSet = MAP_MOOD_TO_NLP.get(key);
                    return nlpSet.stream().anyMatch(message::contains);
                }).findFirst()
                .orElse(CommentMood.NEUTRAL);
        log.info("Found {}", result);
        return result;
    }
}
