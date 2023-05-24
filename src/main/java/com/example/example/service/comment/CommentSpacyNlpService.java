package com.example.example.service.comment;

import com.example.example.client.SpacyClient;
import com.example.example.domain.NlpModel;
import com.example.example.model.CommentMood;
import com.example.example.model.CommentType;
import com.example.example.model.spacy.SpacyModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentSpacyNlpService implements CommentNlpService {

    private final SpacyClient spacyClient;


    private static final Map<CommentType, SpacyModel> COMMENT_TYPE_SPACY_MODEL_MAP = Map.of(
            CommentType.PREPARE_ORDER, SpacyModel.PREPARE_ORDER_SUB_TYPE,
            CommentType.GETTING_ORDER, SpacyModel.GETTING_ORDER_SUB_TYPE,
            CommentType.GOT_ORDER, SpacyModel.GOT_ORDER_SUB_TYPE,
            CommentType.PRODUCT, SpacyModel.PRODUCT_SUB_TYPE,
            CommentType.POST_BOX, SpacyModel.POST_BOX_SUB_TYPE,
            CommentType.DELIVERY, SpacyModel.DELIVERY_SUB_TYPE,
            CommentType.NOTIFICATION, SpacyModel.NOTIFICATION_SUB_TYPE
    );

    @Override
    public List<CommentType> analysis(String comment, BigDecimal temperature) {

        final var analysis = this.spacyClient.getTypes(comment);

        final var mainType = analysis.get(SpacyModel.MAIN_TYPE);
        final var mainTypes = filterByValue(mainType, bigDecimal -> bigDecimal.compareTo(temperature) > 0);
        final var storage = new HashSet<>(mainTypes.keySet());


        mainTypes.forEach((key, value) -> {
            final var model = COMMENT_TYPE_SPACY_MODEL_MAP.get(key);
            final var modelAnalysis = analysis.get(model);

            if (modelAnalysis == null) {
                log.info("Skip model {} at {}", model, key);
                return;
            }

            final var targetResult = filterByValue(modelAnalysis, bigDecimal -> bigDecimal.compareTo(temperature) > 0)
                    .keySet().stream()
                    .filter(commentType -> commentType != CommentType.OTHER)
                    .toList();

            log.info("For model {} found {}", model, targetResult.size());
            storage.addAll(targetResult);
        });

        return storage.stream().toList();
    }

    static <K, V> Map<K, V> filterByValue(Map<K, V> map, Predicate<V> predicate) {
        return map.entrySet()
                .stream()
                .filter(entry -> predicate.test(entry.getValue()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    @Override
    public CommentMood mood(String comment, BigDecimal temperature) {

        Map.Entry<CommentMood, BigDecimal> maxEntry = null;
        for (final var entry : this.spacyClient.getMood(comment).entrySet()) {
            if (maxEntry == null || entry.getValue().compareTo(maxEntry.getValue()) > 0) {
                maxEntry = entry;
            }
        }

        return Objects.requireNonNull(maxEntry).getKey();
    }

    @Override
    public NlpModel getModel() {
        return NlpModel.SPACY;
    }
}
