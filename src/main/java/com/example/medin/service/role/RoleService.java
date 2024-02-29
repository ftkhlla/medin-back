package com.example.medin.service.role;

import com.example.medin.model.entity.Role;

public interface RoleService {
    Role findByName(String name);

    boolean existByName(String name);
}
