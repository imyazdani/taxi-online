package com.example.taxionline.service;

import com.example.taxionline.model.dto.UserDto;

import java.util.List;

public interface UserService {
    UserDto getUser(String username);
    List<UserDto> getAllUsers(Integer pageNo, Integer pageSize, String sortBy);
}
