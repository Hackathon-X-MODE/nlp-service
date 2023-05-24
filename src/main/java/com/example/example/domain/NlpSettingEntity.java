package com.example.example.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@Table(name = "nlp_setting")
@Entity
@ToString
@Accessors(chain = true)
public class NlpSettingEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "nlp_model", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private NlpModel model;

    private BigDecimal temperature;

    private boolean active;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NlpSettingEntity that = (NlpSettingEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
