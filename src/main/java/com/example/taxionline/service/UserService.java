package com.example.taxionline.service;

import com.example.taxionline.model.dto.UserDto;

public interface UserService {
    UserDto getUserByUsername(String username);
}