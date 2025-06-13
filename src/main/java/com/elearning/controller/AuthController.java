package com.elearning.controller;

import com.elearning.dto.user.UserCreateDTO;
import com.elearning.dto.user.UserDTO;
import com.elearning.exception.UserServiceException;
import com.elearning.security.JwtTokenUtil;
import com.elearning.model.User;
import com.elearning.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;

    private final JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserService userService;

    public AuthController(AuthenticationManager authenticationManager, JwtTokenUtil jwtTokenUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @PostMapping("/login")
    public String login(@RequestBody UserDTO user) {
        try {
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
            Authentication authentication = authenticationManager.authenticate(token);
            return jwtTokenUtil.generateToken(authentication.getName());
        } catch (AuthenticationException e) {
            throw new RuntimeException("Invalid username or password");
        }
    }

    @PostMapping("/register")
    public HttpEntity<String> register(@RequestBody UserCreateDTO user) {
        try {
            Boolean success = userService.registerUser(user);
            if(success) {
                return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully");
            }
            return ResponseEntity.badRequest().body("Could not create user");
        } catch (UserServiceException ue) {
            return ResponseEntity.badRequest().body(ue.getMessage());
        }
    }
}
