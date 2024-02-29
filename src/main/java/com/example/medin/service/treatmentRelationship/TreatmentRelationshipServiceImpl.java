package com.example.medin.service.treatmentRelationship;

import com.example.medin.model.dto.RelationshipDto;
import com.example.medin.model.dto.UserDto;
import com.example.medin.model.entity.TreatmentRelationship;
import com.example.medin.model.entity.User;
import com.example.medin.repository.TreatmentRelationshipRepository;
import com.example.medin.service.user.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TreatmentRelationshipServiceImpl implements TreatmentRelationshipService {

    private final TreatmentRelationshipRepository repository;
    private final UserService userService;

    public TreatmentRelationshipServiceImpl(TreatmentRelationshipRepository repository, UserService userService) {
        this.repository = repository;
        this.userService = userService;
    }

    @Override
    public ResponseEntity<?> addPacientToDoctor(String doctorIin, String pacientIin) {
        User doctor = userService.findDoctorByIin(doctorIin);
        User pacient = userService.findPacientByIin(pacientIin);
        TreatmentRelationship oldRelationship = repository.findByDoctorIdAndPacientId(doctor.getId(), pacient.getId());
        if(oldRelationship != null){
            Map<String, String> response = new HashMap<>();
            response.put("message", "This patient has already been added");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        TreatmentRelationship relationship = new TreatmentRelationship(pacient, doctor);
        repository.save(relationship);
        RelationshipDto dto = new RelationshipDto(doctor.getIin(), pacient.getIin());
        Map<String, String> response = new HashMap<>();
        response.put("message", "Relationship created");
        response.put("doctorIin", dto.getDoctorIin());
        response.put("pacientIin", dto.getPacientIin());
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @Override
    public ResponseEntity<?> deletePacientFromDoctor(String doctorIin, String pacientIin){
        User doctor = userService.findDoctorByIin(doctorIin);
        User pacient = userService.findPacientByIin(pacientIin);
        TreatmentRelationship relationship = repository.findByDoctorIdAndPacientId(doctor.getId(), pacient.getId());
        if(relationship == null){
            Map<String, String> response = new HashMap<>();
            response.put("message", "This patient is not bound");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        repository.delete(relationship);
        RelationshipDto dto = new RelationshipDto(doctor.getIin(), pacient.getIin());
        Map<String, String> response = new HashMap<>();
        response.put("message", "Patient successfully removed");
        response.put("doctorIin", dto.getDoctorIin());
        response.put("pacientIin", dto.getPacientIin());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getPacientList(String doctorIin) {
        try {
            User doctor = userService.findDoctorByIin(doctorIin);
            List<TreatmentRelationship> treatmentRelationshipList = repository.findAllByDoctorId(doctor.getId());
            List<UserDto> pacientList = new ArrayList<>();
            for (TreatmentRelationship treatmentRelationship : treatmentRelationshipList) {
                User pacient = userService.findPacientByIin(treatmentRelationship.getPacient().getIin());
                UserDto userDto = new UserDto(pacient);
                pacientList.add(userDto);
            }
            return new ResponseEntity<>(pacientList, HttpStatus.OK);
        } catch (NullPointerException exception) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "User not found");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<?> getDoctorList(String pacientIin) {
        try {
            User pacient = userService.findPacientByIin(pacientIin);
            List<TreatmentRelationship> treatmentRelationshipList = repository.findAllByPacientId(pacient.getId());
            List<UserDto> doctorList = new ArrayList<>();
            for (TreatmentRelationship treatmentRelationship : treatmentRelationshipList) {
                User doctor = userService.findDoctorByIin(treatmentRelationship.getDoctor().getIin());
                UserDto userDto = new UserDto(doctor);
                doctorList.add(userDto);
            }
            return new ResponseEntity<>(doctorList, HttpStatus.OK);
        } catch (NullPointerException exception) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "User not found");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }
}
