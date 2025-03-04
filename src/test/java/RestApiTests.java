import static io.restassured.RestAssured.*;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.is;

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
        String nameData = "{\"name\": \"morpheus\",\"job\": \"leader\"}";
        given()
                .body(nameData)
                .contentType(JSON)
                .when()
                .post("/users")
                .then()
                .log().all()
                .statusCode(201)
                .body("name", is("morpheus"))
                .body("job", is("leader"));
    }

    @DisplayName("Проверка обновления данных пользователя")
    @Test
    void updateUser() {
        String authData = "{\"name\": \"morpheus\",\"job\": \"zion resident\"}";
        given()
                .body(authData)
                .contentType(JSON)
                .when()
                .put("/users/2")
                .then()
                .log().all()
                .statusCode(200)
                .body("name", is("morpheus"))
                .body("job", is("zion resident"));
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
        String authData = "{\"email\": \"eve.holt@reqres.in\",\"password\": \"pistol\"}";
        given()
                .body(authData)
                .contentType(JSON)
                .when()
                .post("/register")
                .then()
                .log().all()
                .statusCode(200)
                .body("id", is(4))
                .body("token", is("QpwL5tke4Pnpja7X4"));
    }

    @DisplayName("Проверка успешной авторизации пользователя")
    @Test
    void authorizationUser() {
        String authUser = "{\"email\": \"eve.holt@reqres.in\",\"password\": \"cityslicka\"}";
        given()
                .body(authUser)
                .contentType(JSON)
                .when()
                .post("/login")
                .then()
                .log().all()
                .statusCode(200)
                .body("token", is("QpwL5tke4Pnpja7X4"));
    }

}