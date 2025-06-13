package com.elearning.dto.user;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class UserCreateDTO {
    private String username;
    private String password;
    private List<String> roles;
}
