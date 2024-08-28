package com.travel.userservice.controller;

import com.travel.userservice.dto.NewUserRequest;
import com.travel.userservice.dto.UserDTO;
import com.travel.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/registration")
public class RegistrationController {
    private final UserService userService;

    // Самый простой вариант для инжекта в спринговые компоненты:
    // 1) Объявляем все поля которые инжектим private final
    // 2) Помечаем класс @RequiredArgsConstructor
    // И так во всех компонентах
    @Autowired
    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED) //201
    public UserDTO postUser(@RequestBody NewUserRequest newUserRequest) {
        return userService.createUser(newUserRequest);
    }

}
