package com.elearning.service;

import com.elearning.dao.AbstractJunitTest;
import com.elearning.dao.RoleDao;
import com.elearning.dao.UserDao;
import com.elearning.dto.user.UserCreateDTO;
import com.elearning.exception.UserServiceException;
import com.elearning.model.Role;
import com.elearning.model.User;
import com.elearning.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


public class UserServiceTest extends AbstractJunitTest {

    @Mock
    private UserDao userDao;

    @Mock
    private RoleDao roleDao;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    private UserCreateDTO userDto;

    @BeforeEach
    void setUp() {
        userDto = UserCreateDTO.builder()
                .username("testuser").password("password123").roles(List.of("USER")).build();
    }

    @Test
    void testRegisterUser_UsernameAlreadyTaken() {
        when(userDao.findByUsername("testuser")).thenReturn(Optional.of(User.builder().build()));

        UserServiceException ue = assertThrows(UserServiceException.class, () -> userService.registerUser(userDto));
        assertEquals("Username is already taken", ue.getMessage());
    }

    @Test
    void testRegisterUser_RoleNotFound() {
        when(userDao.findByUsername("testuser")).thenReturn(Optional.empty());
        when(roleDao.findByName("USER")).thenReturn(null);
        when(passwordEncoder.encode("password123")).thenReturn("encodedPassword");

        UserServiceException ue = assertThrows(UserServiceException.class, () -> userService.registerUser(userDto));
        assertEquals("Role not found: USER", ue.getMessage());
    }

    @Test
    void testRegisterUser_Success() {
        when(userDao.findByUsername("testuser")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("password123")).thenReturn("encodedPassword");
        Role userRole = Role.builder().name("USER").build();
        when(roleDao.findByName("USER")).thenReturn(userRole);

        Boolean response = userService.registerUser(userDto);

        assertTrue(response);
    }

    @Test
    void testRegisterUser_EmptyRoles() {
        userDto.setRoles(List.of()); // No roles provided
        when(userDao.findByUsername("testuser")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("password123")).thenReturn("encodedPassword");

        Boolean response = userService.registerUser(userDto);

        assertTrue(response);
    }
}
