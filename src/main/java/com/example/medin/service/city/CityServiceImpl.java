package com.example.medin.service.city;

import com.example.medin.model.entity.City;
import com.example.medin.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityServiceImpl implements CityService{
    private final CityRepository repository;

    @Autowired
    public CityServiceImpl(CityRepository repository) {
        this.repository = repository;
    }

    @Override
    public City findByName(String name) {
        return repository.findByName(name);
    }

    @Override
    public List<City> findAll() {
        return repository.findAll();
    }
}
