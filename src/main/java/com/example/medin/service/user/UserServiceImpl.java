package com.example.medin.service.user;

import com.example.medin.exceptions.CustomNotFoundException;
import com.example.medin.model.entity.Role;
import com.example.medin.model.entity.User;
import com.example.medin.repository.UserRepository;
import com.example.medin.service.role.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository repository;
    private final RoleService roleService;

    @Autowired
    public UserServiceImpl(UserRepository repository,
                           RoleService roleService) {
        this.repository = repository;
        this.roleService = roleService;
    }

    @Override
    public void save(User user) {
        repository.save(user);
    }

    @Override
    public User findByIin(String iin) {
        return repository.findByIin(iin).orElseThrow(() -> new CustomNotFoundException(
                String.format("Пользователь с иин: %s не найден", iin)));
    }

    @Override
    public User findPacientByIin(String iin) {
        User user = repository.findByIin(iin).orElseThrow(() -> new CustomNotFoundException(
                String.format("Пациент с иин: %s не найден", iin)));
        boolean isPacient = false;
        List<Role> userRoleList = user.getRoles();

        for (Role role : userRoleList) {
            if (role.getName().equals("ROLE_USER")) {
                isPacient = true;
                break;
            }
        }
        if(isPacient){
            return user;
        }
        return null;
    }

    @Override
    public User findDoctorByIin(String iin) {
        User user = repository.findByIin(iin).orElseThrow(() -> new CustomNotFoundException(
                String.format("Доктор с иин: %s не найден", iin)));
        boolean isPacient = false;
        List<Role> userRoleList = user.getRoles();

        for (Role role : userRoleList) {
            if (role.getName().equals("ROLE_DOCTOR")) {
                isPacient = true;
                break;
            }
        }
        if(isPacient){
            return user;
        }
        return null;
    }

    @Override
    public boolean existsByIin(String iin) {
        return repository.existsByIin(iin);
    }

    @Override
    public List<User> findAllByIinContainingIgnoreCase(String iin) {
        return repository.findAllByIinContainingIgnoreCase(iin);
    }

    @Override
    public List<User> findAllByFioContainingIgnoreCase(String fio) {
        return repository.findAllByFioContainingIgnoreCase(fio);
    }

    @Override
    public List<User> findAllByIinContainingIgnoreCaseOrFioContainingIgnoreCase(String iin, String fio) {
        return repository.findAllByIinContainingIgnoreCaseOrFioContainingIgnoreCase(iin, fio);
    }

    @Override
    public List<User> findAll() {
        return repository.findAll();
    }

}
