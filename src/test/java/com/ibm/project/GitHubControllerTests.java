package com.ibm.project;

import static io.restassured.RestAssured.given;
import static org.mockito.BDDMockito.given;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ibm.project.model.Usuario;
import com.ibm.project.model.github.GitHubUser;
import com.ibm.project.model.github.RepositorySummary;
import com.ibm.project.service.GitHubService;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class GitHubControllerTests {
	
	@MockBean
	private GitHubService gitHubService;

    @LocalServerPort
    private int port;

    @Autowired
    ObjectMapper objectMapper;

    private String token;

    @Before
    public void setup() throws Exception {
        RestAssured.port = this.port;
        
        given(this.gitHubService.listarPespositorios(Mockito.any(GitHubUser.class)))
        	.willReturn(Arrays.asList(RepositorySummary.builder().name("test").build()));
        
        token = given()
            .contentType(ContentType.JSON)
            .body(Usuario.builder().username("admin").password("12345").build())
            .when().post("/auth")
            .andReturn().jsonPath().getString("token");
    }

    @Test
    public void testList() throws Exception {
        given()

            .contentType(ContentType.JSON)
            .body(GitHubUser.builder().username("user@git.com").password("password").build())

        .when()
            .post("/list")

        .then()
            .statusCode(403);
    }

    @Test
    public void testListComToken() throws Exception {

        given()
            .header("Authorization", "Bearer "+token)
            .contentType(ContentType.JSON)
            .body(GitHubUser.builder().username("user@git.com").password("password").build())

        .when()
            .post("/list")

        .then()
            .statusCode(200);
    }

}
