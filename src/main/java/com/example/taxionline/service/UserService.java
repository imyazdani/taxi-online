package com.example.taxionline.service;

import com.example.taxionline.model.dto.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    UserDto getUser(String username);
    List<UserDto> getAllUsers(Integer pageNo, Integer pageSize, String sortBy);
}
