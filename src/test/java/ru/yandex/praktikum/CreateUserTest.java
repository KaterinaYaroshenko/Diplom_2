package ru.yandex.praktikum;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.apache.http.HttpStatus;
import org.junit.After;
import org.junit.Test;
import ru.yandex.praktikum.models.User;

import static org.hamcrest.Matchers.equalTo;

public class CreateUserTest extends BaseTest {
    private String token;

    @After
    public void tearDown() {
        if (token != null) {
            userClient.deleteUser(token);
        }
    }

    @Test
    @DisplayName("Создание уникального пользователя")
    @Description("Успешное создание пользователя со всеми обязательными полями")
    public void createUniqueUserSuccess() {
        User user = new User("user_" + System.currentTimeMillis() + "@ya.ru", "pass123", "Andrey");
        var response = userClient.createUser(user);
        token = response.path("accessToken");

        response.then().assertThat().statusCode(HttpStatus.SC_OK)
                .and().body("success", equalTo(true));
    }

    @Test
    @DisplayName("Создание пользователя без пароля")
    @Description("Проверка ошибки 403 при отсутствии поля password")
    public void createUserWithoutPasswordFail() {
        User user = new User("user_" + System.currentTimeMillis() + "@ya.ru", "", "Andrey");
        userClient.createUser(user)
                .then().assertThat().statusCode(HttpStatus.SC_FORBIDDEN)
                .and().body("message", equalTo("Email, password and name are required fields"));
    }

    @Test
    @DisplayName("Создание пользователя без логина (email)")
    @Description("Проверка ошибки 403 при отсутствии поля email")
    public void createUserWithoutEmailFail() {
        User user = new User("", "pass123", "Andrey");
        userClient.createUser(user)
                .then().assertThat().statusCode(HttpStatus.SC_FORBIDDEN)
                .and().body("message", equalTo("Email, password and name are required fields"));
    }
}