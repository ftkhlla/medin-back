package com.example.medin.controller;

import com.example.medin.email.EmailSenderService;
import com.example.medin.exceptions.CustomNotFoundException;
import com.example.medin.model.dto.SearchDto;
import com.example.medin.model.dto.UserDto;
import com.example.medin.model.entity.City;
import com.example.medin.model.entity.User;
import com.example.medin.repository.CityRepository;
import com.example.medin.repository.UserRepository;
import com.example.medin.service.user.UserServiceManagement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserServiceManagement service;
    private final UserRepository repository;
    private final CityRepository cityRepository;
    private final EmailSenderService senderService;

    @GetMapping(value = "/{iin}")
    public ResponseEntity<?> getByIin(@PathVariable String iin) {
        return service.findByIin(iin);
    }

    @PostMapping(value = "/search")
    public ResponseEntity<?> search(@RequestBody SearchDto request) {
        return service.search(request);
    }

    @GetMapping(value = "/drop/password/{email}")
    public ResponseEntity<?> dropPassword(@PathVariable String email) {
        User user = repository.findByEmail(email)
                .orElseThrow(() -> new CustomNotFoundException("Пользователь не найдет по почте"));

        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 8;
        Random random = new Random();

        String randomStr = random.ints(leftLimit, rightLimit + 1)
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();

        user.setPassword(randomStr);
        repository.save(user);
        senderService.sendEmail(user.getEmail(),"Reset password", "Your password is " + randomStr);

        Map<String, String> result = new HashMap<>();
        result.put("message", "Новый пароль отправлен на почту");
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PutMapping(value = "/update")
    public ResponseEntity<?> update (@RequestBody UserDto request){
        User user = repository.findByIin(request.getIin()).orElseThrow(() -> new CustomNotFoundException(
                String.format("Пользователь с иин: %s не найден", request.getIin())));
//        City city = cityRepository.findByName(request.getCity().toLowerCase());

        user.setFio(request.getFio());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setBirthDate(request.getBirthDate());
        user.setEmail(request.getEmail());
        user.setGender(request.getGender());
//        user.setCity(city);
        user.setPassword(request.getPassword());

        repository.save(user);

        Map<String, String> result = new HashMap<>();
        result.put("message", "Данные пользователя обновлены");
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
