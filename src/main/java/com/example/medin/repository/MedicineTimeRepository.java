package com.example.medin.repository;

import com.example.medin.model.entity.MedicineTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedicineTimeRepository extends JpaRepository<MedicineTime, Long> {
    List<MedicineTime> findAllByTreatmentId(Long treatmentId);

    boolean existsByTimeToMedicineAndTreatmentId(String time, Long treatmentId);

    long countAllByTreatmentId(Long treatmentId);
}
