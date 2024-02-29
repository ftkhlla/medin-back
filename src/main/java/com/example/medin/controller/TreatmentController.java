package com.example.medin.controller;

import com.example.medin.model.dto.CommentDto;
import com.example.medin.model.dto.CreateProgressDto;
import com.example.medin.model.dto.RelationshipDto;
import com.example.medin.model.dto.TreatmentDto;
import com.example.medin.service.treatment.TreatmentService;
import com.example.medin.service.treatmentRelationship.TreatmentRelationshipService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/treatment")
public class TreatmentController {

    private final TreatmentService service;
    private final TreatmentRelationshipService relationshipService;

    public TreatmentController(TreatmentService service,
                               TreatmentRelationshipService relationshipService) {
        this.service = service;
        this.relationshipService = relationshipService;
    }


    @PostMapping(value = "/relationship")
    public ResponseEntity<?> addPacientToDoctor(@RequestBody RelationshipDto request) {
        return relationshipService.addPacientToDoctor(request.getDoctorIin(), request.getPacientIin());
    }

    @DeleteMapping(value = "/relationship/{doctorIin}/{pacientIin}")
    public ResponseEntity<?> deletePacientFromDoctor(@PathVariable String doctorIin,
                                                     @PathVariable String pacientIin) {
        return relationshipService.deletePacientFromDoctor(doctorIin, pacientIin);
    }

    @GetMapping(value = "/relationship/pacient/list/{doctorIin}")
    public ResponseEntity<?> getPacientList(@PathVariable String doctorIin) {
        return relationshipService.getPacientList(doctorIin);
    }

    @GetMapping(value = "/relationship/doctor/list/{pacientIin}")
    public ResponseEntity<?> getDoctorList(@PathVariable String pacientIin) {
        return relationshipService.getDoctorList(pacientIin);
    }

    /*----------------------------------------------------------------------------------------------------------------*/

    @PostMapping()
    public ResponseEntity<?> create(@RequestBody TreatmentDto request) {
        return service.create(request);
    }

    @GetMapping("/{pacientIin}/{doctorIin}")
    public ResponseEntity<?> get(@PathVariable String doctorIin,
                                 @PathVariable String pacientIin){
        return service.get(doctorIin, pacientIin);

    }

    @GetMapping("/detail/{pacientIin}/{doctorIin}")
    public ResponseEntity<?> getDetails(@PathVariable String doctorIin,
                                        @PathVariable String pacientIin){
        return service.getDetails(doctorIin, pacientIin);
    }

    /*----------------------------------------------------------------------------------------------------------------*/

    @PostMapping("/progress")
    public ResponseEntity<?> createProgress(@RequestBody CreateProgressDto request){
        return service.createProgress(request);
    }

    @PostMapping("/comment")
    public ResponseEntity<?> createComment(@RequestBody CommentDto request){
        return service.createComment(request);
    }
}
