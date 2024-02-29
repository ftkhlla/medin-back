package com.example.medin.controller;

import com.example.medin.model.dto.LoginDto;
import com.example.medin.model.dto.RegistrationDto;
import com.example.medin.service.user.UserServiceManagement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class Controller {
    private final UserServiceManagement service;

    @Autowired
    public Controller(UserServiceManagement service) {
        this.service = service;
    }

    @PostMapping("/registration")
    public ResponseEntity<?> registration(@RequestBody RegistrationDto request){
        return service.registartion(request);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDto request){
        return service.login(request);
    }
}
