package com.travel.userservice.controller;

import com.travel.userservice.dto.NewUserRequest;
import com.travel.userservice.dto.UserDTO;
import com.travel.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/registration")
public class RegistrationController {
    private final UserService userService;

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED) //201
    public UserDTO postUser(@RequestBody NewUserRequest newUserRequest) {
        return userService.createUser(newUserRequest);
    }

}
