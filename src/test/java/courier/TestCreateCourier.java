package courier;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.equalTo;

public class TestCreateCourier {
    private Courier courier;
    private String idCourier;

    @Before
    public void setUp() {
        courier = CourierActions.createNewCourier();
    }

    @Test
    @DisplayName("Создание курьера")
    @Description("Создание курьера со всеми заполненными полями - Ожидание 201")
    public void createCourier() {
        Response response = CourierActions.createCourier(courier);
        idCourier = CourierActions.getId(courier);
        response.then()
                .log()
                .all()
                .assertThat()
                .statusCode(201)
                .and()
                .body("ok", equalTo(true));
    }

    @Test
    @DisplayName("Создание курьера без логина")
    @Description("Создание курьера с указанием только пароля - Ожидание 400")
    public void createCourierWithoutLogin() {
        courier.setLogin(null);
        Response response = CourierActions.createCourier(courier);
        idCourier = CourierActions.getId(courier);
        response.then()
                .assertThat()
                .statusCode(400).
                and()
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Создание курьера без пароля")
    @Description("Создание курьера с указанием только логина - Ожидание 400")
    public void createCourierWithoutPassword() {
        courier.setPassword(null);
        Response response = CourierActions.createCourier(courier);
        response.then()
                .log()
                .all()
                .assertThat()
                .statusCode(400).
                and()
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Создание двух одинаковых курьеров")
    @Description("Создание второго курьера со существующими данными - Ожидание 409")
    public void createCourierDouble() {
        Response firstResponse = CourierActions.createCourier(courier);
        idCourier = CourierActions.getId(courier);
        firstResponse.then().log().all().assertThat().statusCode(201).and().body("ok", equalTo(true));

        Response secondResponse = CourierActions.createCourier(courier);
        secondResponse.then().log().all().assertThat().statusCode(409)
                .and().body("message", equalTo("Этот логин уже используется"));
    }

    @After
    public void deleteCourier() {
        if (idCourier  != null) {
            CourierActions.deleteCourier(idCourier);
        }
    }
}
