package courier;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

public class TestAutorizationCourier {

    private Courier courier;
    private String idCourier;

    @Before
    public void setUp() {
        courier = CourierActions.createNewCourier();
        CourierActions.createCourier(courier);
        courier.setFirstName(null);
        idCourier = CourierActions.getId(courier);
    }

    @Test
    @DisplayName("Успешный логин")
    @Description("Курьер авторизован - Ожидание 200")
    public void loginCourier() {
        Response response = CourierActions.loginCourier(courier);
        response.then().log().all().assertThat().statusCode(200)
                .and().body("id", notNullValue());
    }

    @Test
    @DisplayName("Авторизация без логина")
    @Description("Авторизация без логина - Ожидание 400")
    public void loginCourierWithoutLogin() {
        courier.setLogin("");
        Response response = CourierActions.loginCourier(courier);
        response.then().log().all().assertThat().statusCode(400)
                .and().body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Авторизация без пароля")
    @Description("Авторизация без пароля - Ожидание 400")
    public void loginCourierWithoutPassword() {
        courier.setPassword("");
        Response response = CourierActions.loginCourier(courier);
        response.then().log().all().assertThat().statusCode(400)
                .and().body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Авторизация с несуществующим логином")
    @Description("Авторизация с несуществующим логином - Ожидание 400")
    public void loginCourierWithNonExistLogin() {
        courier.setLogin(CourierTestData.NOT_EXIST_LOGIN);
        Response response = CourierActions.loginCourier(courier);
        response.then().log().all().assertThat().statusCode(404)
                .and().body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Авторизация с несуществующим паролем")
    @Description("Авторизация с несуществующим паролем - Ожидание 400")
    public void loginCourierWithNonExistPassword() {
        courier.setPassword(CourierTestData.NO_EXIST_PASSWORD);
        Response response = CourierActions.loginCourier(courier);
        response.then().log().all().assertThat().statusCode(404)
                .and().body("message", equalTo("Учетная запись не найдена"));
    }

    @After
    public void deleteCourier() {
        if (idCourier  != null) {
            CourierActions.deleteCourier(idCourier);
        }
    }
}
