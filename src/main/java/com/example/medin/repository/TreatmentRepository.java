package com.example.medin.repository;

import com.example.medin.model.entity.Treatment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TreatmentRepository extends JpaRepository<Treatment, Long> {
    boolean existsByDoctorIdAndPacientId(Long doctorId, Long pacientId);

    List<Treatment> findAllByDoctorIdAndPacientId(Long doctorId, Long pacientId);

    Treatment findByDoctorIdAndPacientId(Long doctorId, Long pacientId);
}
