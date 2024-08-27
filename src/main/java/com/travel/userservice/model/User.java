package com.travel.userservice.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "username")
    private String username;
    @Column(name = "username")
    private String firstName;
    @Column(name = "username")
    private String lastName;
    @Column(name = "telegram_id", unique = true)
    private String telegramId;
    @Column(name = "password")
    private String password;
}
