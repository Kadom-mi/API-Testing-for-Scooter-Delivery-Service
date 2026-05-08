package order;

import contants.EndpointsApi;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import static order.OrderTestData.*;

public class OrderAction extends EndpointsApi {

    public static Order createNewOrder() {
        return new Order(FIRST_NAME,LAST_NAME,ADDRESS,METRO_STATION,PHONE,RENT_TIME,DELIVERY_DATE,COMMENT,COLOR);
    }

    @Step("Создание заказа")
    public static Response createOrder(Order order) {
        return getSpec()
                .body(order)
                .when()
                .post(ORDER);
    }

    @Step("Получение списка заказов")
    public ValidatableResponse getListOrder() {
        return getSpec()
                .when()
                .get(ORDER)
                .then();
    }

    @Step("Отмена заказа")
    public static Response cancelOrder(String trackId) {
        return getSpec()
                .queryParams("track", trackId)
                .when()
                .put(CANCEL_ORDER);
    }
}
