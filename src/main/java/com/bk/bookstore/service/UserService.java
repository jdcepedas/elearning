package com.bk.bookstore.service;

import com.bk.bookstore.model.User;

import java.util.Optional;

public interface UserService {
    User saveUser(User user);

    Optional<User> findByUsername(String username);
}
