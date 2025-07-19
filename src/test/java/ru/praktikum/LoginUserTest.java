package ru.praktikum;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class LoginUserTest extends BaseUserTest {

    @Test
    @DisplayName("Успешный вход под существующим пользователем")
    public void loginWithValidUser() {
        // Создаем пользователя
        user = new RegisterUser()
                .setEmail("user" + System.currentTimeMillis() + "@example.com")
                .setPassword("password123")
                .setName("John");
        userSteps.createUser(user).statusCode(200);

        // Пытаемся залогиниться
        ValidatableResponse response = given()
                .body(user)
                .when()
                .post("/api/auth/login")
                .then()
                .statusCode(200)
                .body("success", is(true))
                .body("accessToken", notNullValue())
                .body("refreshToken", notNullValue());
    }

    @Test
    @DisplayName("Вход с неверным логином или паролем")
    public void loginWithInvalidCredentials() {
        RegisterUser invalidUser = new RegisterUser()
                .setEmail("nonexistent@example.com")
                .setPassword("wrongpassword")
                .setName("NoName");

        ValidatableResponse response = given()
                .body(invalidUser)
                .when()
                .post("/api/auth/login")
                .then()
                .statusCode(401)
                .body("success", is(false))
                .body("message", containsString("email or password are incorrect"));
    }
}
