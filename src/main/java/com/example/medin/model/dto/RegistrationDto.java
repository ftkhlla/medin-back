package com.example.medin.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationDto {
    private String iin;
    private String fio;
    private String phoneNumber;
    private LocalDate birthDate;
    private String email;
    private String password;
    private String gender;
    private String role;
    private String city;
}
