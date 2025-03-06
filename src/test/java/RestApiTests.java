import models.lombok.LombokModelAuthDataRequest;
import models.lombok.LombokModelAuthDataResponse;
import models.lombok.LombokModelJobData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.*;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.hasItems;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static specs.ProjectSpecs.*;


public class RestApiTests extends TestBase {
    @DisplayName("Проверка получения всех пользователей на второй странице")
    @Test
    void getListUsers() {
        step("Get users list on page 2", () ->
                get("/users?page=2")
                        .then()
                        .spec(specWithCode200AndLogAll)
                        .body("data.id", hasItems(7, 8, 9, 10, 11, 12)));

    }

    @DisplayName("Проверка создания пользователя")
    @Test
    void createUser() {
        LombokModelJobData jobData = new LombokModelJobData();
        jobData.setName("morpheus");
        jobData.setJob("leader");
        step("Make request", () ->
                given()
                        .body(jobData)
                        .contentType(JSON)
                        .when()
                        .post("/users")
                        .then()
                        .spec(specWithCode201AndLogAll)
                        .extract().as(LombokModelJobData.class));
        step("Check response", () -> {
            assertEquals("morpheus", jobData.getName());
            assertEquals("leader", jobData.getJob());
        });
    }

    @DisplayName("Проверка обновления данных пользователя")
    @Test
    void updateUser() {
        LombokModelJobData jobData = new LombokModelJobData();
        jobData.setName("morpheus");
        jobData.setJob("zion resident");
        step("Make request", () ->
                given()
                        .body(jobData)
                        .contentType(JSON)
                        .when()
                        .put("/users/2")
                        .then()
                        .spec(specWithCode200AndLogAll)
                        .extract().as(LombokModelJobData.class));
        step("Check response", () -> {
            assertEquals("morpheus", jobData.getName());
            assertEquals("zion resident", jobData.getJob());
        });
    }

    @DisplayName("Удаление пользователя")
    @Test
    void deleteUsers() {
        step("Delete user", () ->
                delete("/users/2")
                        .then()
                        .spec(specWithCode204AndLogAll));
    }

    @DisplayName("Проверка успешной регистрации пользователя")
    @Test
    void registrationUser() {
        LombokModelAuthDataRequest authData = new LombokModelAuthDataRequest();
        authData.setEmail("eve.holt@reqres.in");
        authData.setPassword("pistol");
        LombokModelAuthDataResponse responseData = step("Make request", () ->
                given()
                        .body(authData)
                        .contentType(JSON)
                        .when()
                        .post("/register")
                        .then()
                        .spec(specWithCode200AndLogAll)
                        .extract().as(LombokModelAuthDataResponse.class));
        step("Check response", () -> {
            assertEquals("QpwL5tke4Pnpja7X4", responseData.getToken());
            assertEquals(4, responseData.getId());
        });
    }


    @DisplayName("Проверка успешной авторизации пользователя")
    @Test
    void authorizationUser() {
        LombokModelAuthDataRequest authData = new LombokModelAuthDataRequest();
        authData.setEmail("eve.holt@reqres.in");
        authData.setPassword("pistol");
        LombokModelAuthDataResponse responseData = step("Make request", () ->
                given()
                        .body(authData)
                        .contentType(JSON)
                        .when()
                        .post("/login")
                        .then()
                        .spec(specWithCode200AndLogAll)
                        .extract().as(LombokModelAuthDataResponse.class));
        step("Check response", () ->
                assertEquals("QpwL5tke4Pnpja7X4", responseData.getToken()));

    }

}