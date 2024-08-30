package com.travel.userservice.dto;

import com.travel.userservice.model.User;

public class UserMapper {
    public static UserDTO toDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(user.getUsername());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setTelegramId(user.getTelegramId());
        return userDTO;
    }

    public static User toUser(UserDTO userDTO) {
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setTelegramId(userDTO.getTelegramId());
        return user;
    }

    public static ShortUserDTO toShortDTO(User user) {
        ShortUserDTO shortUserDTO = new ShortUserDTO();
        shortUserDTO.setUsername(user.getUsername());
        shortUserDTO.setFirstName(user.getFirstName());
        shortUserDTO.setLastName(user.getLastName());
        return shortUserDTO;
    }

}
