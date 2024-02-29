package com.example.medin.service.role;

import com.example.medin.model.entity.Role;
import com.example.medin.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService{
    private final RoleRepository repository;

    @Autowired
    public RoleServiceImpl(RoleRepository repository) {
        this.repository = repository;
    }

    @Override
    public Role findByName(String name) {
        return repository.findByName(name);
    }

    @Override
    public boolean existByName(String name) {
        return repository.existsByName(name);
    }
}
