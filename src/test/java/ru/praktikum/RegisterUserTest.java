package ru.praktikum;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.http.HttpStatus;
import org.junit.Test;

import static org.hamcrest.Matchers.*;

@DisplayName("Создание пользователя")
public class RegisterUserTest extends BaseUserTest {
    private String generateRandomEmail() {
        return "user_" + RandomStringUtils.randomAlphabetic(8) + "@example.com";
    }

    @Test
    @DisplayName("Успешная регистрация нового пользователя")
    @Description("Проверяет, что пользователь успешно создан при корректных данных")
    public void userCanBeRegisteredSuccessfully() {
        user = new RegisterUser()
                .setEmail(generateRandomEmail())
                .setPassword("password123")
                .setName("John");
        ValidatableResponse response = userSteps.createUser(user);
        response
                .statusCode(HttpStatus.SC_OK)
                .body("success", is(true))
                .body("accessToken", notNullValue())
                .body("refreshToken", notNullValue());
    }

    @Test
    @DisplayName("Попытка создать уже существующего пользователя")
    @Description("Проверяет, что при создании уже существующего пользователя приходит ошибка 403 Forbidden")
    public void userCannotBeRegisteredTwice() {
        user = new RegisterUser()
                .setEmail(generateRandomEmail())
                .setPassword("password123")
                .setName("John");

        // Первая регистрация
        userSteps.createUser(user)
                .statusCode(HttpStatus.SC_OK)
                .body("success", is(true));

        // Повторная регистрация — ошибка
        ValidatableResponse response = userSteps.createUser(user);
        response
                .statusCode(HttpStatus.SC_FORBIDDEN)
                .body("message", equalTo("User already exists"));
    }

    @Test
    @DisplayName("Нельзя зарегистрировать пользователя без email")
    @Description("Проверяет, что при регистрации пользователя без указания email возвращается ошибка 403 Forbidden")
    public void userCannotBeRegisteredWithoutEmail() {
        user = new RegisterUser()
                .setPassword("password123")
                .setName("John");
        ValidatableResponse response = userSteps.createUser(user);
        response
                .statusCode(HttpStatus.SC_FORBIDDEN)
                .body("message", equalTo("Email, password and name are required fields"));
        user = null; // Не удаляем, потому что не был зарегистрирован
    }

    @Test
    @DisplayName("Нельзя зарегистрировать пользователя без пароля")
    @Description("Проверяет, что при регистрации пользователя без указания пароля возвращается ошибка 403 Forbidden")
    public void userCannotBeRegisteredWithoutPassword() {
        user = new RegisterUser()
                .setEmail(generateRandomEmail())
                .setName("John");

        ValidatableResponse response = userSteps.createUser(user);
        response
                .statusCode(HttpStatus.SC_FORBIDDEN)
                .body("message", equalTo("Email, password and name are required fields"));
        user = null; // Не удаляем, потому что не был зарегистрирован
    }

    @Test
    @DisplayName("Нельзя зарегистрировать пользователя без имени")
    @Description("Проверяет, что при регистрации пользователя без указания имени возвращается ошибка 403 Forbidden")
    public void userCannotBeRegisteredWithoutName() {
        user = new RegisterUser()
                .setEmail(generateRandomEmail())
                .setPassword("password123");

        ValidatableResponse response = userSteps.createUser(user);
        response
                .statusCode(HttpStatus.SC_FORBIDDEN)
                .body("message", equalTo("Email, password and name are required fields"));
        user = null; // Не удаляем, потому что не был зарегистрирован
    }
}



