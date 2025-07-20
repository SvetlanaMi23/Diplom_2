package ru.praktikum.step;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import org.apache.http.HttpStatus;
import ru.praktikum.model.Order;

import java.util.List;

import static io.restassured.RestAssured.given;

public class OrderSteps {
    private static final String ORDERS = "/api/orders";
    private static final String INGREDIENTS = "/api/ingredients";

    @Step("Создание заказа с токеном и ингредиентами")
    public ValidatableResponse createOrderWithToken(String token, List<String> ingredients) {
        Order order = new Order(ingredients);

        return given()
                .header("Authorization", token)
                .body(order)
                .when()
                .post(ORDERS)
                .then();
    }

    @Step("Создание заказа без авторизации")
    public ValidatableResponse createOrderWithoutToken(List<String> ingredients) {
        Order order = new Order(ingredients);

        return given()
                .body(order)
                .when()
                .post(ORDERS)
                .then();
    }

    @Step("Получение списка ингредиентов")
    public ValidatableResponse getIngredients() {
        return given()
                .when()
                .get(INGREDIENTS)
                .then();
    }

    @Step("Получение id первого ингредиента из списка")
    public String getFirstIngredientId() {
        return getIngredients()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .path("data[0]._id");
    }
}
