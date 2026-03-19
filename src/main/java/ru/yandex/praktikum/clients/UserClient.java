package ru.yandex.praktikum.clients;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.yandex.praktikum.models.User;

public class UserClient extends BaseClient {

    @Step("Создать пользователя")
    public Response createUser(User user) {
        return getSpec().body(user).post("/api/auth/register");
    }

    @Step("Логин пользователя")
    public Response login(User user) {
        return getSpec().body(user).post("/api/auth/login");
    }

    @Step("Удалить пользователя")
    public Response deleteUser(String token) {
        if (token == null) return null;
        return getSpec().header("Authorization", token).delete("/api/auth/user");
    }
}