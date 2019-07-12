package com.ibm.project;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.notNullValue;
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
public class AuthenticationControllerTests {
	
    @LocalServerPort
    private int port;

    //private String token;

    @Before
    public void setup() throws Exception {
        RestAssured.port = this.port;
    }

    @Test
    public void testAutenticacaoComFalha() throws Exception {
        given()
            .contentType(ContentType.JSON)
            .body(User.builder().username("admin").password("teste").build())

        .when()
            .post("/auth")

        .then()
            .statusCode(403);
    }

    @Test
    public void testAutenticacaoComSucesso() throws Exception {

        given()            
            .contentType(ContentType.JSON)
            .body(User.builder().username("admin").password("12345").build())

        .when()
            .post("/auth")
        .then()                
        	.statusCode(200)
        	.assertThat()        	        	
        	.body("token", notNullValue() );
    }

}
