package com.example.medin.service.user;

import com.example.medin.model.dto.LoginDto;
import com.example.medin.model.dto.RegistrationDto;
import com.example.medin.model.dto.SearchDto;
import org.springframework.http.ResponseEntity;

public interface UserServiceManagement {
    ResponseEntity<?> registartion(RegistrationDto dto);

    ResponseEntity<?> login (LoginDto dto);

    ResponseEntity<?> findByIin(String iin);

    ResponseEntity<?> search(SearchDto request);
}
