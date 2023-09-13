package com.example.taxionline.service.impl;

import com.example.taxionline.exception.UserNotFoundException;
import com.example.taxionline.model.dto.UserDto;
import com.example.taxionline.model.entity.UserEntity;
import com.example.taxionline.model.enums.UserRoleEnum;
import com.example.taxionline.repository.UserRepository;
import com.example.taxionline.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {
    @Mock
    UserRepository userRepository;

    ModelMapper modelMapper;

    UserService userService;
    UserEntity userEntity;

    @BeforeEach
    void setUp() {
        modelMapper = new ModelMapper();

        this.userService = new UserServiceImpl(this.userRepository, modelMapper);

        userEntity = new UserEntity();
        userEntity.setId(1L);
        userEntity.setName("mohsen yazdani");
        userEntity.setRole(UserRoleEnum.PASSENGER);
        userEntity.setUsername("mohsen");
        // password is: mohsen
        userEntity.setPassword("$2a$10$fIFKI0z4HcdCzb8YkR9Dfu5g13RdYGM1ScC/ebH9JN5SVcbtDvdYW");
    }

    @Test
    void testGetUser() {
        String username = "mohsen";
        Mockito.when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(userEntity));

        UserDto userDto = userService.getUser(username);

        Assertions.assertNotNull(userDto);
        Assertions.assertEquals(userEntity.getId(), userDto.getId());
        Assertions.assertEquals(userEntity.getName(), userDto.getName());
        Assertions.assertEquals(userEntity.getUsername(), userDto.getUsername());
        Assertions.assertEquals(userEntity.getPassword(), userDto.getPassword());
        Assertions.assertEquals(userEntity.getRole(), userDto.getRole());
    }

    @Test
    void testGetUser_UserNotFound() {
        String username = "mohsen";
        Mockito.when(userRepository.findByUsername(anyString())).thenReturn(Optional.empty());

        Assertions.assertThrows(UserNotFoundException.class, () -> userService.getUser(username));
    }

    @Test
    void testGetAllUsers() {
        Integer pageNo = 0;
        Integer pageSize = 10;
        String sortBy = "id";

        Page<UserEntity> pageResult = new PageImpl<UserEntity>(List.of(userEntity));
        Mockito.when(userRepository.findAll((Pageable) any())).thenReturn(pageResult);
        List<UserDto> userDtoList = userService.getAllUsers(pageNo, pageSize, sortBy);

        Assertions.assertNotNull(userDtoList);
        Assertions.assertEquals(userDtoList.size(), 1);
    }

    @Test
    void testGetAllUsers_IsEmpty() {
        Integer pageNo = 0;
        Integer pageSize = 10;
        String sortBy = "id";

        Page<UserEntity> pageResult = new PageImpl<UserEntity>(List.of());
        Mockito.when(userRepository.findAll((Pageable) any())).thenReturn(pageResult);
        List<UserDto> userDtoList = userService.getAllUsers(pageNo, pageSize, sortBy);

        Assertions.assertNotNull(userDtoList);
        Assertions.assertEquals(userDtoList.size(), 0);
    }

    @Test
    void testLoadUserByUsername() {
        String username = "mohsen";

        UserDto userDto = new UserDto();
        userDto.setUsername(userEntity.getUsername());
        userDto.setPassword(userEntity.getPassword());


        Mockito.when(userRepository.findByUsername(anyString())).thenReturn(Optional.ofNullable(userEntity));

        UserDetails userDetails = userService.loadUserByUsername(username);

        Assertions.assertNotNull(userDetails);
        Assertions.assertEquals(userDetails.getUsername(), userEntity.getUsername());
        Assertions.assertEquals(userDetails.getPassword(), userEntity.getPassword());
    }

    @Test
    void testLoadUserByUsername_notFound() {
        String username = "mohsen";

        Mockito.when(userRepository.findByUsername(anyString())).thenReturn(Optional.empty());

        Assertions.assertThrows(UsernameNotFoundException.class, () -> userService.loadUserByUsername(username));
    }
}