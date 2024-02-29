package com.example.medin.service.city;

import com.example.medin.model.entity.City;

import java.util.List;

public interface CityService {
    City findByName(String name);

    List<City> findAll();
}
