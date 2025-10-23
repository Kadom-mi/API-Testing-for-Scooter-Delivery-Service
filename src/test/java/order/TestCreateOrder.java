package order;

import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import static order.OrderTestData.*;
import static org.hamcrest.CoreMatchers.notNullValue;

@RunWith(Parameterized.class)
public class TestCreateOrder {

    private String[] color;
    private Order order;
    private String track;

    public TestCreateOrder(String[] color) {
        this.color = color;
        this.order = new Order(FIRST_NAME, LAST_NAME, ADDRESS, METRO_STATION,
                PHONE, RENT_TIME, DELIVERY_DATE, COMMENT, color);
    }

    @Parameterized.Parameters(name = "Test {index} Цвет самоката: {0}")
    public static Object[][] getColor() {
        return new Object[][]{
                {new String[]{"BLACK"}},
                {new String[]{"GREY"}},
                {new String[]{"BLACK", "GREY"}},
                {null}
        };
    }

    @Test
    @DisplayName("Создание заказа")
    @Description("Заказ можно создать с указанием только одного цвета, обоих цветов, либо без их указания в принципе")
    public void createOrder () {
        String colorDisplay;
        if (color == null) {
            colorDisplay = "не указан";
        } else if (color.length == 1) {
            colorDisplay = color[0].equals("BLACK") ? "чёрный" : "серый";
        } else {
            colorDisplay = "оба цвета";
        }
        Allure.parameter("Цвет самоката", colorDisplay);
        Response response = OrderAction.createOrder(order);
        track = response.then().extract().path("track").toString();
        response.then().log().all().assertThat().statusCode(201)
                .and()
                .assertThat()
                .body("track", notNullValue());
    }
}

