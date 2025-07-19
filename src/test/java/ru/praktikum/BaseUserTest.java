package ru.praktikum;

import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.junit.After;
import org.junit.Before;
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
                    .statusCode(anyOf(is(200), is(202), is(204)));
        } else if (user != null) {
            // Если токена нет, попробовать получить и удалить
            try {
                String token = userSteps.loginAndGetToken(user);
                if (token != null && token.startsWith("Bearer")) {
                    userSteps.deleteUser(token)
                            .statusCode(anyOf(is(200), is(202), is(204)));
                }
            } catch (Exception e) {
                System.out.println("Не удалось удалить пользователя: " + e.getMessage());
            }
        }
    }
}

