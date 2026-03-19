package ru.yandex.praktikum;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.apache.http.HttpStatus;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.praktikum.models.Order;
import ru.yandex.praktikum.models.User;

import java.util.List;

import static org.hamcrest.Matchers.equalTo;

public class CreateOrderTest extends BaseTest {
    private String token;
    private List<String> ingredientIds;

    @Before
    public void prepare() {

        User user = new User("order_user_" + System.currentTimeMillis() + "@ya.ru", "p12345", "OrderBot");
        token = userClient.createUser(user).path("accessToken");


        ingredientIds = orderClient.getIngredients().path("data._id");
    }

    @After
    public void cleanup() {
        if (token != null) userClient.deleteUser(token);
    }

    @Test
    @DisplayName("Создание заказа авторизованным пользователем")
    @Description("Успешное создание заказа при наличии валидного токена")
    public void createOrderAuthorizedSuccess() {
        Order order = new Order(List.of(ingredientIds.get(0), ingredientIds.get(1)));
        orderClient.createOrder(order, token)
                .then().assertThat().statusCode(HttpStatus.SC_OK)
                .and().body("success", equalTo(true));
    }

    @Test
    @DisplayName("Создание заказа без авторизации")
    @Description("Проверка, что заказ можно создать без токена (API Stellar Burgers это позволяет)")
    public void createOrderUnauthorizedSuccess() {
        Order order = new Order(List.of(ingredientIds.get(0)));
        orderClient.createOrder(order, null)
                .then().assertThat().statusCode(HttpStatus.SC_OK)
                .and().body("success", equalTo(true));
    }

    @Test
    @DisplayName("Создание заказа без ингредиентов")
    @Description("Ошибка 400 при пустом списке ингредиентов")
    public void createOrderWithoutIngredientsFail() {
        Order order = new Order(List.of());
        orderClient.createOrder(order, token)
                .then().assertThat().statusCode(HttpStatus.SC_BAD_REQUEST)
                .and().body("message", equalTo("Ingredient ids must be provided"));
    }

    @Test
    @DisplayName("Создание заказа с неверным хешем ингредиентов")
    @Description("Ошибка 500 при передаче невалидного ID ингредиента")
    public void createOrderInvalidHashFail() {
        Order order = new Order(List.of("invalid_hash_123"));
        orderClient.createOrder(order, token)
                .then().assertThat().statusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR);
    }
}