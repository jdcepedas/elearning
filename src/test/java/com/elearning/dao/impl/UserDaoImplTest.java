package com.elearning.dao.impl;

import com.elearning.dao.AbstractJunitTest;
import com.elearning.dao.UserDao;
import com.elearning.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class UserDaoImplTest extends AbstractJunitTest {

    @Autowired
    private UserDao userDao;

    @Test
    void saveAndFindByUsername() {
        User user = User.builder()
                .username("testuser")
                .password("password")
                .build();
        userDao.save(user);

        Optional<User> found = userDao.findByUsername("testuser");
        assertTrue(found.isPresent());
        assertEquals("testuser", found.get().getUsername());
    }
} 