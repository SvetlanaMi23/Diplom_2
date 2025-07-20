package ru.praktikum.step;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import org.apache.http.HttpStatus;
import ru.praktikum.model.RegisterUser;

import static io.restassured.RestAssured.given;

public class UserSteps {
    private static final String CREATE_USER = "/api/auth/register";
    private static final String LOGIN_USER = "/api/auth/login";
    private static final String DELETE_USER = "/api/auth/user";

    @Step("Создание пользователя: {user}")
    public ValidatableResponse createUser(RegisterUser user){
        return given()
                .header("Content-type", "application/json")
                .body(user)
                .when()
                .post(CREATE_USER)
                .then();
    }

    @Step("Логин пользователя и получение accessToken: {user}")
    public String loginAndGetToken(RegisterUser user) {
        ValidatableResponse response = login(user)
                .statusCode(HttpStatus.SC_OK);

        // Извлекаем accessToken из тела ответа
        String token = response.extract().path("accessToken");
        return token; // должен уже содержать "Bearer "
    }

    @Step("Логин пользователя: {user}")
    public ValidatableResponse login(RegisterUser user) {
        return given()
                .body(new RegisterUser().setEmail(user.getEmail()).setPassword(user.getPassword()))
                .when()
                .post(LOGIN_USER)
                .then();
    }

    @Step("Удаление пользователя по accessToken")
    public ValidatableResponse deleteUser(String accessToken) {
        return given()
                .header("Authorization", accessToken) // токен уже с "Bearer "
                .when()
                .delete(DELETE_USER)
                .then();
    }
}
