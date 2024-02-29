package com.example.medin.repository;

import com.example.medin.model.entity.TreatmentRelationship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TreatmentRelationshipRepository extends JpaRepository<TreatmentRelationship, Long> {

    List<TreatmentRelationship> findAllByDoctorId(Long doctorId);

    List<TreatmentRelationship> findAllByPacientId(Long pacientId);

    TreatmentRelationship findByDoctorIdAndPacientId(Long doctorId, Long pacientId);
}
