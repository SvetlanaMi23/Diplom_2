package ru.praktikum;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;
import ru.praktikum.step.OrderSteps;
import ru.praktikum.model.RegisterUser;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.*;

@DisplayName("Создание заказа")
public class OrderTest extends BaseUserTest {

    private final OrderSteps orderSteps = new OrderSteps();
    private String accessToken;
    private String validIngredientId;

    @Before
    public void setUp() {
        user = new RegisterUser()
                .setEmail("user" + System.currentTimeMillis() + "@mail.ru")
                .setPassword("123456")
                .setName("TestUser");

        userSteps.createUser(user).statusCode(HttpStatus.SC_OK); //регистрация нового пользователя
        this.accessToken = userSteps.loginAndGetToken(user); //выполняется логин и сохраняется accessToken
        validIngredientId = orderSteps.getFirstIngredientId(); //id первого ингредиента
    }

    @Test
    @DisplayName("Создание заказа с авторизацией и валидными ингредиентами")
    @Description("Авторизованный пользователь может создать заказ с правильными ингредиентами")
    public void createOrderWithAuthAndValidIngredientsTest() {
        orderSteps.createOrderWithToken(accessToken, List.of(validIngredientId))
                .statusCode(HttpStatus.SC_OK)
                .body("success", is(true))
                .body("order.number", notNullValue())
                .body("name", notNullValue());
    }

    @Test
    @DisplayName("Создание заказа без авторизации, но с валидными ингредиентами")
    @Description("Гость может создать заказ без авторизации")
    public void createOrderWithoutAuthTest() {
        orderSteps.createOrderWithoutToken(List.of(validIngredientId))
                .statusCode(HttpStatus.SC_OK)
                .body("success", is(true))
                .body("order.number", notNullValue())
                .body("name", notNullValue());
    }

    @Test
    @DisplayName("Создание заказа без ингредиентов")
    @Description("Создание заказа без ингредиентов должно вернуть ошибку 400")
    public void createOrderWithoutIngredientsTest() {
        orderSteps.createOrderWithToken(accessToken, Collections.emptyList())
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body("success", is(false))
                .body("message", equalTo("Ingredient ids must be provided"));
    }

    @Test
    @DisplayName("Создание заказа с невалидным id ингредиента")
    @Description("Передача несуществующего id ингредиента возвращает 500")
    public void createOrderWithInvalidIngredientTest() {
        orderSteps.createOrderWithToken(accessToken, List.of("invalid_ingredient_id"))
                .statusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR); // Internal Server Error
    }
}

