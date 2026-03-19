package ru.yandex.praktikum.clients;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.yandex.praktikum.models.Order;

public class OrderClient extends BaseClient {

    @Step("Получить список ингредиентов")
    public Response getIngredients() {
        return getSpec().get("/api/ingredients");
    }

    @Step("Создать заказ")
    public Response createOrder(Order order, String token) {
        var request = getSpec();
        if (token != null) request.header("Authorization", token);
        return request.body(order).post("/api/orders");
    }
}