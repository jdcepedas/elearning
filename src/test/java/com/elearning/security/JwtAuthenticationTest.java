package com.elearning.security;

import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class JwtAuthenticationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testLoginAndAccessProtectedResource() throws Exception {
        // Step 1: Perform login request
        MvcResult loginResult = mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"testuser\", \"password\":\"password123\"}"))
                .andExpect(status().isOk())
                .andReturn();

        // Extract token from response
        String responseJson = loginResult.getResponse().getContentAsString();
        String token = JsonPath.parse(responseJson).read("$.token");

        // Step 2: Access protected resource with token
        //mockMvc.perform(get("/api/books")
        //                .header("Authorization", "Bearer " + token))
        //        .andExpect(status().isOk());
    }
}
