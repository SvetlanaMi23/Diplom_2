package ru.praktikum;

import io.qameta.allure.junit4.DisplayName;
import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;
import ru.praktikum.model.RegisterUser;

import static org.hamcrest.Matchers.*;


@DisplayName("Логин пользователя")
public class LoginUserTest extends BaseUserTest {

    @Before
    public void setUp() {
        // Создаем пользователя
        user = new RegisterUser()
                .setEmail("user" + System.currentTimeMillis() + "@example.com")
                .setPassword("password123")
                .setName("John");
        userSteps.createUser(user).statusCode(HttpStatus.SC_OK);
    }


    @Test
    @DisplayName("Успешный вход под существующим пользователем")
    public void loginWithValidUserTest() {
        // Пытаемся залогиниться
        userSteps.login(user)
                .statusCode(HttpStatus.SC_OK)
                .body("success", is(true))
                .body("accessToken", notNullValue())
                .body("refreshToken", notNullValue());
    }

    @Test
    @DisplayName("Вход с неверным логином")
    public void loginWithInvalidLoginTest() {
        RegisterUser invalidUser = new RegisterUser()
                .setEmail("nonexistent@example.com")
                .setPassword(user.getPassword())
                .setName(user.getName());

        userSteps.login(invalidUser)
                .statusCode(HttpStatus.SC_UNAUTHORIZED)
                .body("success", is(false))
                .body("message", containsString("email or password are incorrect"));
    }

    @Test
    @DisplayName("Вход с неверным паролем")
    public void loginWithInvalidPasswordTest() {
        RegisterUser invalidUser = new RegisterUser()
                .setEmail(user.getEmail())
                .setPassword("wrongpassword")
                .setName(user.getName());

        userSteps.login(invalidUser)
                .statusCode(HttpStatus.SC_UNAUTHORIZED)
                .body("success", is(false))
                .body("message", containsString("email or password are incorrect"));
    }
}
