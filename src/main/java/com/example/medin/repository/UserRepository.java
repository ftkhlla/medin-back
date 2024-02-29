package com.example.medin.repository;

import com.example.medin.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByIin(String iin);

    boolean existsByIin(String iin);

    List<User> findAllByIinContainingIgnoreCase(String iin);

    List<User> findAllByFioContainingIgnoreCase(String fio);

    List<User> findAllByIinContainingIgnoreCaseOrFioContainingIgnoreCase(String iin, String fio);

    Optional<User> findByEmail(String email);
}
