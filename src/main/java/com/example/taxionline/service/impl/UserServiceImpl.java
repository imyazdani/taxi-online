package com.example.taxionline.service.impl;

import com.example.taxionline.exception.UserNotFoundException;
import com.example.taxionline.model.dto.UserDto;
import com.example.taxionline.model.entity.UserEntity;
import com.example.taxionline.repository.UserRepository;
import com.example.taxionline.security.SecurityConfig;
import com.example.taxionline.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    public UserDto getUser(String username) {
        UserEntity userEntity = userRepository.findByUsername(username).orElseThrow(() -> {
            throw new UserNotFoundException(username);
        });

        return modelMapper.map(userEntity, UserDto.class);
    }

    @Override
    public List<UserDto> getAllUsers(Integer pageNo, Integer pageSize, String sortBy) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Page<UserEntity> pagedResult = userRepository.findAll(paging);

        if (pagedResult.hasContent()) {
            return modelMapper.map(pagedResult.getContent(), new TypeToken<ArrayList<UserDto>>() {
            }.getType());
        } else {
            return new ArrayList<UserDto>();
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            UserDto userDto = getUser(username);
            List<GrantedAuthority> grantedAuthorityList = new ArrayList<>();
            grantedAuthorityList.add(
                    new SimpleGrantedAuthority(SecurityConfig.rolePrefix + userDto.getRole().toString()));

            return new User(userDto.getUsername(), userDto.getPassword(),
                    true, true, true,
                    true, grantedAuthorityList);

        } catch (UserNotFoundException e) {
            throw new UsernameNotFoundException(String.format("Username with name %s not found.", username));
        }
    }
}
