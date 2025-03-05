import static io.restassured.RestAssured.*;
import static io.restassured.http.ContentType.JSON;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasItems;
import static org.junit.jupiter.api.Assertions.assertEquals;

import models.lombok.LombokModelAuthDataRequest;
import models.lombok.LombokModelAuthDataResponse;
import models.lombok.LombokModelJobData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


public class RestApiTests extends TestBase {
    @DisplayName("Проверка получения всех пользователей на второй странице")
    @Test
    void getListUsers() {
        get("/users?page=2")
                .then()
                .log().all()
                .statusCode(200)
                .body("data.id", hasItems(7, 8, 9, 10, 11, 12));
    }

    @DisplayName("Проверка создания пользователя")
    @Test
    void createUser() {
        LombokModelJobData jobData = new LombokModelJobData();
        jobData.setName("morpheus");
        jobData.setJob("leader");
        given()
                .body(jobData)
                .contentType(JSON)
                .when()
                .post("/users")
                .then()
                .log().all()
                .statusCode(201)
                .extract().as(LombokModelJobData.class);
        assertEquals("morpheus", jobData.getName());
        assertEquals("leader", jobData.getJob());
    }

    @DisplayName("Проверка обновления данных пользователя")
    @Test
    void updateUser() {
        LombokModelJobData jobData = new LombokModelJobData();
        jobData.setName("morpheus");
        jobData.setJob("zion resident");
        given()
                .body(jobData)
                .contentType(JSON)
                .when()
                .put("/users/2")
                .then()
                .log().all()
                .statusCode(200)
                .extract().as(LombokModelJobData.class);
        assertEquals("morpheus", jobData.getName());
        assertEquals("zion resident", jobData.getJob());
    }

    @DisplayName("Удаление пользователя")
    @Test
    void deleteUsers() {
        delete("/users/2")
                .then()
                .log().all()
                .statusCode(204);
    }

    @DisplayName("Проверка успешной регистрации пользователя")
    @Test
    void registrationUser() {
        LombokModelAuthDataRequest authData = new LombokModelAuthDataRequest();
        authData.setEmail("eve.holt@reqres.in");
        authData.setPassword("pistol");
        LombokModelAuthDataResponse responseData =
                given()
                        .body(authData)
                        .contentType(JSON)
                        .when()
                        .post("/register")
                        .then()
                        .log().all()
                        .log().body()
                        .statusCode(200)
                        .extract().as(LombokModelAuthDataResponse.class);
        assertEquals("QpwL5tke4Pnpja7X4", responseData.getToken());
        assertEquals(4, responseData.getId());
    }


    @DisplayName("Проверка успешной авторизации пользователя")
    @Test
    void authorizationUser() {
        LombokModelAuthDataRequest authData = new LombokModelAuthDataRequest();
        authData.setEmail("eve.holt@reqres.in");
        authData.setPassword("pistol");
        LombokModelAuthDataResponse responseData =
                given()
                        .body(authData)
                        .contentType(JSON)
                        .when()
                        .post("/login")
                        .then()
                        .log().all()
                        .statusCode(200)
                        .extract().as(LombokModelAuthDataResponse.class);
        assertEquals("QpwL5tke4Pnpja7X4", responseData.getToken());

    }

}