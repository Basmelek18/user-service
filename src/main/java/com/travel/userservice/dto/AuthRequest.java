package com.travel.userservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
// private final поля
// @RequiredArgsConstructor
// По умолчанию желательно  создавать объекты иммутабельными: т.е. все поля private final, объект создаем через конструктор
// Если уже появляется необходимость вешаем сеттеры на отдельные поля
public class AuthRequest {
    private String username;
    private String password;
}
