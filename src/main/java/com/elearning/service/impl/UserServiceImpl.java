package com.elearning.service.impl;

import com.elearning.dao.RoleDao;
import com.elearning.dto.user.UserCreateDTO;
import com.elearning.exception.UserServiceException;
import com.elearning.model.Role;
import com.elearning.model.User;
import com.elearning.dao.UserDao;
import com.elearning.service.UserService;
import lombok.Builder;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Data
@Builder
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User saveUser(User user) {
        return userDao.save(user);
    }

    public Optional<User> findByUsername(String username) {
        return userDao.findByUsername(username);
    }

    public Boolean registerUser(UserCreateDTO userDto) {
        if (userDao.findByUsername(userDto.getUsername()).isPresent()) {
            throw new UserServiceException("Username is already taken");
        }

        String encodedPassword = passwordEncoder.encode(userDto.getPassword());
        Set<Role> roles;
        if(userDto.getRoles() != null) {
            roles = userDto
                    .getRoles()
                    .stream()
                    .map(roleName -> Optional.ofNullable(roleDao.findByName(roleName))
                            .orElseThrow(() -> new UserServiceException("Role not found: " + roleName)))
                    .collect(Collectors.toSet());
        } else {
            roles = Set.of();
        }
        // Create new user
        User user = User.builder()
                .username(userDto.getUsername())
                .password(encodedPassword)
                .roles(roles)
                .build();

        userDao.save(user);
        return true;
    }
}
