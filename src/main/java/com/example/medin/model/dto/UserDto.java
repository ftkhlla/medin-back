package com.example.medin.model.dto;

import com.example.medin.model.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private String iin;
    private String fio;
    private String phoneNumber;
    private LocalDate birthDate;
    private String email;
    private String gender;
    private String role;
    private String city;

    private String password;

    public UserDto(User user) {
        this.iin = user.getIin();
        this.fio = user.getFio();
        this.phoneNumber = user.getPhoneNumber();
        this.birthDate = user.getBirthDate();
        this.email = user.getEmail();
        this.gender = user.getGender();
        this.role = user.getRoles().get(0).getName();
        this.city = user.getCity().getName();
    }
}
