package com.elearning.service;

import com.elearning.model.Role;

import java.util.Optional;

public interface RoleService {

    Role saveRole(Role role);

    Optional<Role> findByName(String name);
}
