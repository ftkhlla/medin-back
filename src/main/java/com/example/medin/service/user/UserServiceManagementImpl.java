package com.example.medin.service.user;

import com.example.medin.model.entity.City;
import com.example.medin.model.entity.Role;
import com.example.medin.model.entity.User;
import com.example.medin.model.dto.LoginDto;
import com.example.medin.model.dto.RegistrationDto;
import com.example.medin.model.dto.SearchDto;
import com.example.medin.model.dto.UserDto;
import com.example.medin.service.city.CityService;
import com.example.medin.service.role.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class UserServiceManagementImpl implements UserServiceManagement {
    private final UserService userService;
    private final RoleService roleService;
    private final CityService cityService;

    @Autowired
    public UserServiceManagementImpl(UserService userService,
                                     RoleService roleService,
                                     CityService cityService) {
        this.userService = userService;
        this.roleService = roleService;
        this.cityService = cityService;
    }

    @Override
    public ResponseEntity<?> registartion(RegistrationDto dto) {
        Map<String, String> result = new HashMap<>();
        boolean existsUser = userService.existsByIin(dto.getIin());
        if (existsUser) {
            result.put("result", "Пользователь с таким ИИН существует");
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
        boolean existsRole = roleService.existByName(dto.getRole());
        if (!existsRole) {
            result.put("result", "Такой роли не существует");
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }

        Role role = roleService.findByName(dto.getRole());
        City city = cityService.findByName(dto.getCity().toLowerCase());

        User user = new User(dto, Collections.singletonList(role), city);
        userService.save(user);

        result.put("result", "Registration successful");
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    public ResponseEntity<?> login(LoginDto dto) {
        boolean exists = userService.existsByIin(dto.getIin());
        Map<String, String> result = new HashMap<>();
        if (!exists) {
            result.put("result", "Пользователь с таким ИИН существует");
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }

        User user = userService.findByIin(dto.getIin());

        if (!user.getPassword().equals(dto.getPassword())) {
            result.put("result", "Password wrong!");
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }

        List<Role> rolesList = user.getRoles();
        for (Role role : rolesList) {
            if (role.getName().equals(dto.getRole()) && user.getPassword().equals(dto.getPassword())) {
                result.put("result", "Login successful");
                return new ResponseEntity<>(result, HttpStatus.OK);
            }
        }
        result.put("result", "Something wrong!");
        return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<?> findByIin(String iin) {
        User user = userService.findByIin(iin);
        UserDto dto = new UserDto(user);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> search(SearchDto request) {
        List<User> userList = null;
        if ((request.getIin() == null && request.getFio() == null) ||
                (request.getIin().equals("") && request.getFio().equals(""))) {
            userList = userService.findAll();
        }
        if (request.getFio() == null || request.getFio().equals("")) {
            userList = userService.
                    findAllByIinContainingIgnoreCase(request.getIin());
        }
        if (request.getIin() == null || request.getIin().equals("")) {
            userList = userService.
                    findAllByFioContainingIgnoreCase(request.getIin());
        }
        if (request.getIin() != null && request.getFio() != null){
            userList = userService.
                    findAllByIinContainingIgnoreCaseOrFioContainingIgnoreCase(request.getIin(), request.getFio());
        }
            List<UserDto> userDtoList = castUserListToUserDtoList(userList);
        return new ResponseEntity<>(userDtoList, HttpStatus.OK);


    }

    private List<UserDto> castUserListToUserDtoList(List<User> userList) {
        List<UserDto> userDtoList = new ArrayList<>();
        for (User user : userList) {
            UserDto userDto = new UserDto(user);
            userDtoList.add(userDto);
        }
        return userDtoList;
    }
}
