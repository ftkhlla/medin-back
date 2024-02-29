package com.example.medin.service.user;

import com.example.medin.model.entity.User;

import java.util.List;

public interface UserService {
    void save(User user);

    User findByIin(String iin);

    User findPacientByIin(String iin);

    User findDoctorByIin(String iin);

    boolean existsByIin(String iin);

    List<User> findAllByIinContainingIgnoreCase(String iin);

    List<User> findAllByFioContainingIgnoreCase(String iin);

    List<User> findAllByIinContainingIgnoreCaseOrFioContainingIgnoreCase(String iin, String fio);

    List<User> findAll();
}
