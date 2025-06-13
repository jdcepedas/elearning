package com.elearning.service;

import com.elearning.dto.user.UserCreateDTO;
import com.elearning.model.User;
import org.springframework.http.ResponseEntity;

import java.util.Optional;


public interface UserService {
    User saveUser(User user);

    Optional<User> findByUsername(String username);

    Boolean registerUser(UserCreateDTO userDto);
}
