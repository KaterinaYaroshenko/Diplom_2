package ru.yandex.praktikum;

import org.junit.Before;
import ru.yandex.praktikum.clients.OrderClient;
import ru.yandex.praktikum.clients.UserClient;

public class BaseTest {
    protected UserClient userClient;
    protected OrderClient orderClient;

    @Before
    public void setUp() {

        userClient = new UserClient();
        orderClient = new OrderClient();
    }
}