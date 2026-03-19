package ru.yandex.praktikum.models;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private String email;
    private String password;
    private String name;
}