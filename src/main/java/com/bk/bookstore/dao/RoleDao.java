package com.bk.bookstore.dao;

import com.bk.bookstore.model.Role;

public interface RoleDao extends GenericDao<Role, Long> {
    Role findByName(String name);
}