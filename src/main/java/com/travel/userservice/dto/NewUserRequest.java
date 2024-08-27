package com.travel.userservice.dto;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NewUserRequest {
    private String username;
    private String firstName;
    private String lastName;
    private String telegramId;
    private String password;
}
