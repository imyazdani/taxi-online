package com.example.taxionline.model.dto;

import com.example.taxionline.model.enums.UserRoleEnum;
import lombok.Data;

@Data
public class UserDto {
    private Long id;
    private String name;
    private String username;
    private String password;
    private UserRoleEnum role;
}
