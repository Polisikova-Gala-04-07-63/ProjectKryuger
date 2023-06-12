package tests;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.Random;

public class BaseTest {
    final static String BASE_URI = "https://studio-api.softr.io/v1/api";
    final static String API_KEY = "khIbAyJIU5CIuh1oDuBRx1s49";
    final static String DOMAIN = "jere237.softr.app";
    public static String deleteEndpoint = "v1/api/users/";

    public String generateRandomEmail() {
        Random randomGenerator = new Random();
        int randomInt = randomGenerator.nextInt(1000);
        String randomEmail = "User" + "gmail.com";
        return randomEmail;
    }

    static RequestSpecification specification = new RequestSpecBuilder()
            .setUrlEncodingEnabled(false)
            .setBaseUri(BASE_URI)
            .setContentType(ContentType.JSON)
            .addHeader("Softr-Api-Key", API_KEY)
            .addHeader("Softr-Domain", DOMAIN)
            .build();

    public static Response getRequest(String endPoint, Integer responseCode) {
        Response response = RestAssured.given()
                .spec(specification)
                .when()
                .log().all()
                .get(endPoint)
                .then().log().all()
                .extract().response();
        response.then().assertThat().statusCode(responseCode);
        return response;
    }

    public Response getRequestWithParam(String endPoint, Integer responseCode, String paramName, int id) {
        Response response = RestAssured.given()
                .spec(specification)
                .when()
                .pathParam(paramName, id)
                .log().all()
                .get(endPoint)
                .then().log().all()
                .extract().response();
        response.then().assertThat().statusCode(responseCode);
        return response;
    }

    public Response postRequest(String endPoint, Integer responseCode, Object body) {
        Response response = RestAssured.given()
                .spec(specification)
                .body(body)
                .when()
                .log().all()
                .post(endPoint)
                .then().log().all()
                .extract().response();
        response.then().assertThat().statusCode(responseCode);
        return response;
    }

    public Response putRequest(String endPoint, Integer responseCode, Object body) {
        Response response = RestAssured.given()
                .spec(specification)
                .body(body)
                .when()
                .log().all()
                .put(endPoint)
                .then().log().all()
                .extract().response();
        response.then().assertThat().statusCode(responseCode);
        return response;
    }

    public Response deleteRequest(String endPoint, Integer responseCode) {

        Response response = RestAssured.given()
                .spec(specification)
                .when()
                .log().all()
                .delete(endPoint)
                .then().log().all()
                .extract().response();
        response.then().assertThat().statusCode(responseCode);
        return response;
    }

}
