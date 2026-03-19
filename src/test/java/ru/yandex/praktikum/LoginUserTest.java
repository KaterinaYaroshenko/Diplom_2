package ru.yandex.praktikum;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.apache.http.HttpStatus;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.praktikum.models.User;
import static org.hamcrest.Matchers.equalTo;

public class LoginUserTest extends BaseTest {
    private User user;
    private String token;

    @Before
    public void regUser() {
        user = new User("login_" + System.currentTimeMillis() + "@ya.ru", "pass123", "Ivan");
        token = userClient.createUser(user).path("accessToken");
    }

    @After
    public void tearDown() { userClient.deleteUser(token); }

    @Test
    @DisplayName("Вход под существующим пользователем")
    public void loginSuccess() {
        userClient.login(user)
                .then().assertThat().statusCode(HttpStatus.SC_OK).body("success", equalTo(true));
    }

    @Test
    @DisplayName("Вход с неверным паролем")
    public void loginWithWrongPassword() {
        User wrongPassUser = new User(user.getEmail(), "wrong_pass", user.getName());
        userClient.login(wrongPassUser)
                .then().assertThat().statusCode(HttpStatus.SC_UNAUTHORIZED)
                .and().body("message", equalTo("email or password are incorrect"));
    }

    @Test
    @DisplayName("Вход с неверным логином (email)")
    public void loginWithWrongEmail() {
        User wrongEmailUser = new User("non_existent_user_" + System.currentTimeMillis() + "@ya.ru", user.getPassword(), user.getName());

        userClient.login(wrongEmailUser)
                .then().assertThat().statusCode(HttpStatus.SC_UNAUTHORIZED)
                .and().body("message", equalTo("email or password are incorrect"));
    }

}