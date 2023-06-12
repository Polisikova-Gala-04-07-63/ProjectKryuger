package tests;

import dto.CreateUserRequest;
import dto.CreateUserResponse;
import dto.InvalidErrorResponse;
import dto.ValidUserRequest;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import org.testng.annotations.Test;

import java.lang.reflect.Type;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;
import static org.testng.AssertJUnit.assertNotNull;

public class CreateUserTest extends BaseTest {
    String endpoint = "/users";

    @Test
    public void successfulCreateUser() {

        ValidUserRequest requestBody = ValidUserRequest.builder()
                .email("hkkdsddffdsdasjh@gmail.com")
                .full_name("SFGGJSH")
                .password("123456")
                .generate_magic_link(false)
                .build();

        Response response = postRequest(endpoint, 201, requestBody);
        assertEquals(201, response.getStatusCode());

    }

    @Test
    public void getUser() {

        given().baseUri("https://studio-api.softr.io/v1/api")
                .when().log().all()
                .post("/users")
                .then().log().all();
    }

    @Test
    public void testCreateNewUser() {
        CreateUserRequest request = new CreateUserRequest("SFGGJSH", "hkkdsddffdsdasjh@gmail.com", "123456", "false");
        CreateUserResponse response = given()
                .baseUri(BASE_URI)
                .body(request)
                .header("Softr-Api-Key", API_KEY)
                .header("Softr-Domain", DOMAIN)
                .when().log().all()
                .contentType(ContentType.JSON)
                .post("/v1/api/users")
                .then().log().all()
                .statusCode(201)
                .extract().body().as(CreateUserResponse.class);
        assertNotNull(response.getEmail());
        assertNotNull(response.getCreated());
        assertNotNull(response.getUpdated());
        assertNotNull(response.getFull_name());
        assertEquals(request.getFull_name(), response.getFull_name());
        assertEquals(request.getEmail(), response.getEmail());

        given()
                .baseUri(BASE_URI)
                .header("Softr-Api-Key", API_KEY)
                .header("Softr-Domain", DOMAIN)
                .when().log().all()
                .contentType(ContentType.JSON)
                .delete("vi/api/users/" + request.getEmail())
                .then().log().all()
                .statusCode(200);
    }

    @Test
    public void testCreateUser() {
        CreateUserRequest request = new CreateUserRequest("John Richardson", "hfzrgf@gmail.com", "123456", "false");
        CreateUserResponse response = given()
                .baseUri(BASE_URI)
                .body(request)
                .header("Softr-Api-Key", API_KEY)
                .header("Softr-Domain", DOMAIN)
                .when().log().all()
                .contentType(ContentType.JSON)
                .post("/v1/api/users")
                .then().log().all()
                .statusCode(201)
                .extract().body().as(CreateUserResponse.class);
        assertNotNull(response.getEmail());
        assertNotNull(response.getCreated());
        assertNotNull(response.getUpdated());
        assertNotNull(response.getFull_name());
        assertEquals(request.getFull_name(), response.getFull_name());
        assertEquals(request.getEmail(), response.getEmail());

    }

    @Test
    public void invalidEmail() {
        CreateUserRequest request = new CreateUserRequest("SFGGJSH", "", "12345", "false");
        InvalidErrorResponse response = given()
                .baseUri(BASE_URI)
                .body(request)
                .header("Softr-Api-Key", API_KEY)
                .header("Softr-Domain", DOMAIN)
                .when().log().all()
                .contentType(ContentType.JSON)
                .post("/v1/api/users")
                .then().log().all()
                .statusCode(400)
                .extract().body().as(InvalidErrorResponse.class);
        assertEquals("Something went wrong, please try again.", response.getMessage());
        assertEquals("Bad Request", response.getCode());
    }

    @Test
    public void invalidPassword() {
        String generateEmail = generateRandomEmail();
        CreateUserRequest request = CreateUserRequest.builder()
                .full_name("FGN HGHj")
                .email(generateEmail)
                .generate_magic_link("false").build();
        CreateUserResponse response = given()
                .baseUri(BASE_URI)
                .body(request)
                .header("Softr-Api-Key", API_KEY)
                .header("Softr-Domain", DOMAIN)
                .when().log().all()
                .contentType(ContentType.JSON)
                .post("/v1/api/users")
                .then().log().all()
                .statusCode(400)
                .extract().body().as((Type) InvalidErrorResponse.class);
        assertEquals(request.getFull_name(), response.getFull_name());
        assertEquals(request.getEmail(), response.getEmail());
        assertNotNull(response.getCreated());
        assertNotNull(response.getUpdated());
        given()
                .baseUri(BASE_URI)
                .header("Softr-Api-Key", API_KEY)
                .header("Softr-Domain", DOMAIN)
                .when().log().all()
                .contentType(ContentType.JSON)
                .delete("vi/api/users/" + request.getEmail())
                .then().log().all()
                .statusCode(200);
    }

}


