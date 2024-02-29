package com.example.medin.repository;

import com.example.medin.model.entity.TreatmentProgress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface TreatmentProgressRepository extends JpaRepository<TreatmentProgress, Long> {
    boolean existsByTimeAndTreatmentId(LocalDateTime time, Long TreatmentId);

    TreatmentProgress findByTimeAndTreatmentId(LocalDateTime time, Long TreatmentId);
}
