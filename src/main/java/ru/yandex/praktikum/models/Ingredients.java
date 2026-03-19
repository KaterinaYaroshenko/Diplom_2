package ru.yandex.praktikum.models;

import lombok.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Ingredients {
    private List<String> ingredients;
}