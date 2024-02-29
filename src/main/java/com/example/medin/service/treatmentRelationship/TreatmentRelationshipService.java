package com.example.medin.service.treatmentRelationship;

import org.springframework.http.ResponseEntity;

public interface TreatmentRelationshipService {
    ResponseEntity<?> addPacientToDoctor(String doctorIin, String pacientIin);

    ResponseEntity<?> deletePacientFromDoctor(String doctorIin, String pacientIin);

    ResponseEntity<?> getPacientList(String doctorIin);

    ResponseEntity<?> getDoctorList(String pacientIin);
}
