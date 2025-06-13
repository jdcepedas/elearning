package com.elearning.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
@Rollback
@Transactional
@WebAppConfiguration
public abstract class AbstractJunitTest {

    @BeforeEach
    public void init() {
        try(AutoCloseable ignored = MockitoAnnotations.openMocks(this)) {

        } catch (Exception e) {
            throw new RuntimeException("");
        }
    }

}
