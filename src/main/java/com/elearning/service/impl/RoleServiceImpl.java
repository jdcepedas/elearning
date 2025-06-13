package com.elearning.service.impl;

import com.elearning.dao.RoleDao;
import com.elearning.model.Role;
import com.elearning.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleDao roleDao;


    @Override
    public Role saveRole(Role role) {
        return roleDao.save(role);
    }

    @Override
    public Optional<Role> findByName(String name) {
        return Optional.empty();
    }
}
