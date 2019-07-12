package com.ibm.project;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import com.ibm.project.model.User;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class UserControllerTests {
	
    @LocalServerPort
    private int port;

    private String token;

    @Before
    public void setup() throws Exception {
        RestAssured.port = this.port;
                
        token = given()
            .contentType(ContentType.JSON)
            .body(User.builder().username("admin").password("12345").build())
            .when().post("/auth")
            .andReturn().jsonPath().getString("token");
    }

    @Test
    public void testObterUsuarioComFalha() throws Exception {
        given()
        .when()
            .get("/me")
        .then()
            .statusCode(403);
    }

    @Test
    public void testObterUsuario() throws Exception {

        given()
            .header("Authorization", "Bearer "+token)
        .when()
            .get("/me")
        .then()
            .statusCode(200)
            .and()
            .assertThat()
            .body("username", equalTo("admin"));
    }

}
