package com.example.medin.model.entity;

import com.example.medin.model.dto.RegistrationDto;
import com.example.medin.model.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "iin", unique = true)
    private String iin;

    @Column(name = "fio")
    private String fio;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "gender")
    private String gender;

    @ManyToMany
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(
                    name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id"))
    private List<Role> roles;

    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;

    public User(RegistrationDto dto, List<Role> roleList, City city) {
        this.iin = dto.getIin();
        this.fio = dto.getFio();
        this.phoneNumber = dto.getPhoneNumber();
        this.birthDate = dto.getBirthDate();
        this.email = dto.getEmail();
        this.gender = dto.getGender();
        this.password = dto.getPassword();
        this.roles = roleList;
        this.city = city;
    }
}
