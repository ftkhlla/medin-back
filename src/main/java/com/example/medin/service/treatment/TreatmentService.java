package com.example.medin.service.treatment;

import com.example.medin.model.dto.CommentDto;
import com.example.medin.model.dto.CreateProgressDto;
import com.example.medin.model.dto.TreatmentDto;
import org.springframework.http.ResponseEntity;

public interface TreatmentService {

    ResponseEntity<?> create(TreatmentDto request);

    ResponseEntity<?> get(String doctorIin, String pacientIin);

    ResponseEntity<?> getDetails(String doctorIin, String pacientIin);

    ResponseEntity<?> createProgress(CreateProgressDto request);

    ResponseEntity<?> createComment(CommentDto request);
}
