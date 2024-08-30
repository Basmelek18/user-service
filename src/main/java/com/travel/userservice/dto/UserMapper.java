package com.travel.userservice.dto;

import com.travel.userservice.model.User;

public class UserMapper {
    public static UserDTO toDTO(User user) {
        return UserDTO.builder()
                .username(user.getUsername())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .telegramId(user.getTelegramId())
                .build();
    }

//    public static User toUser(UserDTO userDTO) {
//        User user = new User();
//        user.setUsername(userDTO.getUsername());
//        user.setTelegramId(userDTO.getTelegramId());
//        return user;
//    }

    public static ShortUserDTO toShortDTO(User user) {
        return ShortUserDTO.builder()
                .username(user.getUsername())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .build();
    }

}
