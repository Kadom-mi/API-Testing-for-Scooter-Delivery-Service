package contants;

import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import static io.restassured.RestAssured.given;

public class EndpointsApi {
    public static final String URL = "https://qa-scooter.praktikum-services.ru";
    public static final String COURIER = "api/v1/courier/";
    public static final String LOGIN = "api/v1/courier/login/";
    public static final String ORDER = "api/v1/orders";
    public static final String CANCEL_ORDER = "api/v1/orders/cancel";

    public static RequestSpecification getSpec() {
        return given()
                .contentType(ContentType.JSON)
                .baseUri(URL)
                .log()
                .all();
    }
}
