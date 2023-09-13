package com.example.taxionline.controller;

import com.example.taxionline.model.dto.UserDto;
import com.example.taxionline.model.response.UserInfoRs;
import com.example.taxionline.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final ModelMapper modelMapper;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping
    ResponseEntity<List<UserInfoRs>> getAllUsers(
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "5") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy)
    {
        List<UserDto> userDtoList = userService.getAllUsers(pageNo, pageSize, sortBy);
        List<UserInfoRs> userInfoList = modelMapper.map(userDtoList, new TypeToken<List<UserInfoRs>>(){}.getType());
        return new ResponseEntity<>(userInfoList, HttpStatus.OK);
    }

}
