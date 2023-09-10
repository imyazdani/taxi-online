package com.example.taxionline.service.impl;

import com.example.taxionline.exception.UserNotFoundException;
import com.example.taxionline.model.dto.UserDto;
import com.example.taxionline.model.entity.UserEntity;
import com.example.taxionline.repository.UserRepository;
import com.example.taxionline.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    @Override
    public UserDto getUser(String username) {
        UserEntity userEntity = userRepository.findByUsername(username).orElseThrow(() -> {
            throw new UserNotFoundException("no value present in Optional object");
        });

        return modelMapper.map(userEntity, UserDto.class);
    }
}
