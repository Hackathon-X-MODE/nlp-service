package com.example.example.repository;

import com.example.example.domain.NlpSettingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface NlpSettingRepository extends JpaRepository<NlpSettingEntity, UUID> {


    Optional<NlpSettingEntity> findByActiveTrue();
}
