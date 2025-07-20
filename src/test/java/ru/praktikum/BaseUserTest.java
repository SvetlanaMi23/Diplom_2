package ru.praktikum;

import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.apache.http.HttpStatus;
import org.junit.After;
import org.junit.Before;
import ru.praktikum.model.RegisterUser;
import ru.praktikum.step.UserSteps;

import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.is;

public class BaseUserTest extends BaseTest {

    protected RegisterUser user;
    protected String accessToken;
    protected final UserSteps userSteps = new UserSteps();

    @Before
    public void setUp() {
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
    }

    @After
    public void tearDown() {
        if (accessToken != null && !accessToken.isEmpty()) {
            // Если токен уже есть — сразу удалить
            userSteps.deleteUser(accessToken)
                    .statusCode(anyOf(is(HttpStatus.SC_OK), is(HttpStatus.SC_ACCEPTED), is(HttpStatus.SC_NO_CONTENT)));
        } else if (user != null) {
            // Если токена нет, попробовать получить и удалить
            try {
                String token = userSteps.loginAndGetToken(user);
                if (token != null && token.startsWith("Bearer")) {
                    userSteps.deleteUser(token)
                            .statusCode(anyOf(is(HttpStatus.SC_OK), is(HttpStatus.SC_ACCEPTED), is(HttpStatus.SC_NO_CONTENT)));
                }
            } catch (Exception e) {
                System.out.println("Не удалось удалить пользователя: " + e.getMessage());
            }
        }
    }
}

