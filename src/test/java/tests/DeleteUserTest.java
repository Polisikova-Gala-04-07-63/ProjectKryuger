package tests;

import dto.*;
import io.restassured.http.ContentType;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;

public class DeleteUserTest extends BaseTest {

    String endpoint = "/users/";
    String email = "hkkdsddffdsdasjh@gmail.com";

    @BeforeMethod
    public void setUp() {
        ValidUserRequest requestBody = ValidUserRequest.builder()
                .email(email)
                .full_name("SFGGJSH")
                .password("123456")
                .generate_magic_link(false)
                .build();

        postRequest(endpoint, 201, requestBody);
    }

    @Test
    public void successDelete() {
        deleteRequest(endpoint + email, 200);
    }

    @Test
    public void testDeleteUser() {
        String generateEmail = generateRandomEmail();
        CreateUserRequest request = new CreateUserRequest("SFGGJSH", generateEmail, "123456", "false");
        given()
                .baseUri(BASE_URI)
                .body(request)
                .header("Softr-Api-Key", API_KEY)
                .header("Softr-Domain", DOMAIN)
                .when().log().all()
                .contentType(ContentType.JSON)
                .post("/v1/api/users")
                .then().log().all()
                .statusCode(201);

        given()
                .baseUri(BASE_URI)
                .header("Softr-Api-Key", API_KEY)
                .header("Softr-Domain", DOMAIN)
                .when().log().all()
                .contentType(ContentType.JSON)
                .delete("vi/api/users/")
                .then().log().all()
                .statusCode(200);
    }

    @Test
    public void testDeleteInvalidUser() {
        String generateEmail = generateRandomEmail();
        CreateUserRequest request = new CreateUserRequest("SFGGJSH", generateEmail, "123456", "false");
        given()
                .baseUri(BASE_URI)
                .body(request)
                .header("Softr-Api-Key", API_KEY)
                .header("Softr-Domain", DOMAIN)
                .when().log().all()
                .contentType(ContentType.JSON)
                .post("/v1/api/users")
                .then().log().all()
                .statusCode(201);

        given()
                .baseUri(BASE_URI)
                .header("Softr-Api-Key", API_KEY)
                .header("Softr-Domain", DOMAIN)
                .when().log().all()
                .contentType(ContentType.JSON)
                .delete("vi/api/users/")
                .then().log().all()
                .statusCode(200);

        InvalidDeleteUserResponse response = given()
                .baseUri(BASE_URI)
                .header("Softr-Api-Key", API_KEY)
                .header("Softr-Domain", DOMAIN)
                .when().log().all()
                .contentType(ContentType.JSON)
                .delete("v1/api/users/" + request.getEmail())
                .then().log().all()
                .statusCode(404)
                .extract().body().as(InvalidDeleteUserResponse.class);
        assertEquals("Not Found", response.getCode());
        assertEquals("User with email: " + request.getEmail() + "not found", response.getMessage());

    }

}
