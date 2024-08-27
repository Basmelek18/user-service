package com.travel.userservice.controller;

import com.travel.userservice.dto.ShortUserDTO;
import com.travel.userservice.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public ShortUserDTO getUser(@PathVariable long userId) {
        return userService.getUser(userId);
    }


}
