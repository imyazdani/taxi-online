package com.example.taxionline.model.response;

import com.example.taxionline.model.enums.UserRoleEnum;
import lombok.Data;

@Data
public class UserInfoRs {
    private Long id;
    private String name;
    private String username;
    private UserRoleEnum role;
}
