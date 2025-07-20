package ru.praktikum;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import org.apache.http.HttpStatus;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class Order {
    private static final String ORDERS = "/api/orders";
    private static final String INGREDIENTS = "/api/ingredients";

    @Step("Создание заказа с токеном и ингредиентами")
    public ValidatableResponse createOrderWithToken(String token, List<String> ingredients) {
        return given()
                .header("Authorization", token)
                .body(Map.of("ingredients", ingredients))
                .when()
                .post(ORDERS)
                .then();
    }

    @Step("Создание заказа без авторизации")
    public ValidatableResponse createOrderWithoutToken(List<String> ingredients) {
        return given()
                .body(Map.of("ingredients", ingredients))
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
