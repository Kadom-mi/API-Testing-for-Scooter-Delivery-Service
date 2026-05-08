package order;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;
import java.util.HashMap;
import java.util.List;
import static org.junit.Assert.*;

public class TestGetOrders {
    private final OrderAction orderAction = new OrderAction();

    @DisplayName("Получение списка заказов")
    @Description("Проверка, что в тело ответа возвращается непустой список заказов")
    @Test
    public void getOrdersList(){
        ValidatableResponse responseCreate = orderAction.getListOrder();
        int actualStatusCodeCreate = responseCreate.extract().statusCode();
        List<HashMap> orderBody = responseCreate.extract().path("orders");
        assertEquals(200, actualStatusCodeCreate);
        assertNotNull(orderBody);
        assertTrue(orderBody.get(0).get("id").toString().matches("[\\d]+"));
    }
}
