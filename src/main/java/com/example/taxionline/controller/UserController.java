package com.example.taxionline.controller;

import com.example.taxionline.model.dto.UserDto;
import com.example.taxionline.model.response.UserInfoRs;
import com.example.taxionline.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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
@SecurityRequirement(name = "basicAuth")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final ModelMapper modelMapper;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping
    @Operation(summary = "Get All users are exist in the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get list of users successfully",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = UserInfoRs.class))) })})
    ResponseEntity<List<UserInfoRs>> getAllUsers(
            @Parameter(description = "page number of result") @RequestParam(defaultValue = "0") Integer pageNo,
            @Parameter(description = "page size of result") @RequestParam(defaultValue = "5") Integer pageSize,
            @Parameter(description = "result to be sort by specific field") @RequestParam(defaultValue = "id") String sortBy)
    {
        List<UserDto> userDtoList = userService.getAllUsers(pageNo, pageSize, sortBy);
        List<UserInfoRs> userInfoList = modelMapper.map(userDtoList, new TypeToken<List<UserInfoRs>>(){}.getType());
        return new ResponseEntity<>(userInfoList, HttpStatus.OK);
    }

}
