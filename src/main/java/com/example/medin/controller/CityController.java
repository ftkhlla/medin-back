package com.example.medin.controller;

import com.example.medin.model.entity.City;
import com.example.medin.service.city.CityService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/city")
public class CityController {
    private final CityService service;

    public CityController(CityService service) {
        this.service = service;
    }

    @GetMapping("/list")
    public List<City> findAll() {
        return service.findAll();
    }
}
