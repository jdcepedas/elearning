package com.elearning.dao;

import com.elearning.model.User;

import java.util.Optional;

public interface UserDao extends GenericDao<User, Long> {
    Optional<User> findByUsername(String username);
}
