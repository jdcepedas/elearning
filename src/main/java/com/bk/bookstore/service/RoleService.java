package com.bk.bookstore.service;

import com.bk.bookstore.model.Role;

import java.util.Optional;

public interface RoleService {

    Role saveRole(Role role);

    Optional<Role> findByName(String name);
}
