package com.bk.bookstore.service.impl;

import com.bk.bookstore.dao.RoleDao;
import com.bk.bookstore.model.Role;
import com.bk.bookstore.service.RoleService;
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
