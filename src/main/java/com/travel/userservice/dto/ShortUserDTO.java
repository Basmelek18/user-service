package com.travel.userservice.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@RequiredArgsConstructor
public class ShortUserDTO {
    private final String username;
    private final String firstName;
    private final String lastName;
}
