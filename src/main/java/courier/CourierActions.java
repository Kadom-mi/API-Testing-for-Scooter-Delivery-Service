package courier;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import static contants.EndpointsApi.*;
import static io.restassured.RestAssured.given;

public class CourierActions extends CourierTestData{

    public static Courier createNewCourier() {
        return new Courier(EXIST_LOGIN, EXIST_PASSWORD, FIRST_NAME);
    }

    @Step("Создание курьера")
    public static Response createCourier(Courier courier) {
        return getSpec()
                .body(courier)
                .when()
                .post(COURIER);
    }

    @Step("Авторизация курьера")
    public static Response loginCourier(Courier courier) {
        return getSpec()
                .body(courier)
                .when()
                .post(LOGIN);
    }

    @Step("Получить id при успешной регистрации курьера для того, чтобы потом его удалить")
    public static String getId(Courier courier) {
        String id;
        try {
            id = loginCourier(courier)
                    .then()
                    .extract()
                    .path("id").toString();
        } catch (Exception e) {
            id = null;
        }
        return id;
    }

    @Step("Удаление курьера")
    public static void deleteCourier(String courierId) {
        given()
                .baseUri(URL)
                .delete(COURIER + courierId);
    }
}
