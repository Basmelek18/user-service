package com.travel.userservice.dto;

import com.travel.userservice.model.User;

public class UserMapper {
    public static UserDTO toDTO(User user) {
        // Лучше создание объектов делать через конструктор
        // Если очень много аргументов в конструкторе, то через билдер чтобы улучшить читаемость
        // сеттеры используются когда нужно чтото записать в объект во время работы с ним
        // Если мы просто создаем объект и больше ничего не планируем в него писать, лучше вообще не создавать сеттеры
        UserDTO userDTO = new UserDTO();
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
