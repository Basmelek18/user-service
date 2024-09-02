package com.travel.userservice.controller;

import com.travel.userservice.authentification.JwtTokenUtil;
import com.travel.userservice.dto.ShortUserDTO;
import com.travel.userservice.dto.UserDTO;
import com.travel.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(path="/user")
public class UserController {
    private final UserService userService;
    private final JwtTokenUtil jwtTokenUtil;

    @GetMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public ShortUserDTO getUser(@PathVariable long userId) {
        return userService.getUser(userId);
    }

    @GetMapping("/me")
    @ResponseStatus(HttpStatus.OK)
    public UserDTO getMe(@RequestHeader("Authorization") String authorizationHeader) {
        String token = null;
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            token = authorizationHeader.substring(7);
        }
        return userService.getMe(jwtTokenUtil.extractUsername(token));
    }

    @PostMapping("/me")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDTO updateMe(@RequestBody UserDTO userDTO) {
        return userService.updateUser(userDTO);
    }


}
