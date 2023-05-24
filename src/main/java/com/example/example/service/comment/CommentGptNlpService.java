package com.example.example.service.comment;

import com.example.example.client.ChatGpt;
import com.example.example.domain.NlpModel;
import com.example.example.model.CommentMood;
import com.example.example.model.CommentType;
import com.example.example.service.CommentDirectory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Slf4j
@Service
public class CommentGptNlpService implements com.example.example.service.comment.CommentNlpService {

    private static final String TEMPLATE_CRITERIA = """
            Выпиши категории для описания отзыва о доставке в постамат
            Категории:
            %_criteria_%
                        
            Какие категории подходят для отзыва об доставке товара в постамат, отзыв: "%_comment_%"
                        
            Выпиши только категории.
            """;

    private static final String TEMPLATE_MOOD = """
            Позитивный ли окрас у сообщения "%_comment_%" Ответь одним словом
            """;

    private static final Map<CommentMood, Set<String>> MAP_MOOD_TO_NLP = Map.of(
            CommentMood.POSITIVE, Set.of("да", "позитив"),
            CommentMood.NEGATIVE, Set.of("нет", "негатив")
    );

    private final String template;
    private final ChatGpt chatGpt;
    private final Map<String, CommentType> commentDirectory;

    public CommentGptNlpService(ChatGpt chatGpt, CommentDirectory commentDirectory) {
        this.chatGpt = chatGpt;
        this.commentDirectory = commentDirectory.getStorage();


        this.template = TEMPLATE_CRITERIA.replace("%_criteria_%", String.join("\n", this.commentDirectory.keySet()));
        log.info("Generated template: \n{}", this.template);
    }

    @Override
    public List<CommentType> analysis(String comment, BigDecimal temperature) {
        log.info("Calculate message {}", comment.strip());
        final var message = this.chatGpt.message(this.template.replace("%_comment_%", comment.strip())).toLowerCase();
        final var result = this.commentDirectory.keySet().stream().filter(message::contains).map(this.commentDirectory::get).toList();
        log.info("Found {}", result);
        return result;
    }

    @Override
    public CommentMood mood(String comment, BigDecimal temperature) {
        final var message = this.chatGpt.message(TEMPLATE_MOOD.replace("%_comment_%", comment.strip())).toLowerCase();

        final var result = MAP_MOOD_TO_NLP.keySet().stream().filter(key -> {
                    final var nlpSet = MAP_MOOD_TO_NLP.get(key);
                    return nlpSet.stream().anyMatch(message::contains);
                }).findFirst()
                .orElse(CommentMood.NEUTRAL);
        log.info("Found {}", result);
        return result;
    }

    @Override
    public NlpModel getModel() {
        return NlpModel.CHAT_GPT;
    }
}
