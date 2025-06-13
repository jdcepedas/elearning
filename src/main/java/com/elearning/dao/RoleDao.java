package com.elearning.dao;

import com.elearning.model.Role;

public interface RoleDao extends GenericDao<Role, Long> {
    Role findByName(String name);
}