package com.travel.userservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NewUserRequest {
    private String username;
    private String telegramId;
    private String password;
}
