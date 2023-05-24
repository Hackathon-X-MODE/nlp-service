package com.example.example.service.nlp;

import com.example.example.domain.NlpModel;
import com.example.example.domain.NlpSettingEntity;
import com.example.example.exception.EntityNotFoundException;
import com.example.example.repository.NlpSettingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class NlpService {


    private final NlpSettingRepository repository;


    @Transactional(readOnly = true)
    public NlpSettingEntity getActiveModel() {
        return this.repository.findByActiveTrue()
                .orElseThrow(
                        () -> new EntityNotFoundException("Can't find activated model!")
                );
    }

    @Transactional
    public void changeActiveModel(NlpModel nlpModel) {
        this.repository.findAll().forEach(nlpSettingEntity -> nlpSettingEntity.setActive(nlpSettingEntity.getModel() == nlpModel));
    }
}
