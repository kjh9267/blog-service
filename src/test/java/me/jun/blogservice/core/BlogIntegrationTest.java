package me.jun.blogservice.core;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import me.jun.blogservice.core.application.dto.CreateArticleRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import redis.embedded.RedisServer;

import static io.restassured.RestAssured.given;
import static me.jun.blogservice.support.ArticleFixture.createArticleRequest;
import static me.jun.blogservice.support.ArticleFixture.updateArticleRequest;
import static me.jun.blogservice.support.RedisFixture.REDIS_PORT;
import static me.jun.blogservice.support.TokenFixture.createToken;
import static me.jun.blogservice.support.WriterFixture.WRITER_ID;
import static org.hamcrest.Matchers.hasKey;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@ActiveProfiles("test")
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = "spring.cloud.config.enabled=false"
)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class BlogIntegrationTest {

    @LocalServerPort
    private int port;

    private static String token;

    private RedisServer redisServer;

    private static final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .create();

    @BeforeEach
    void setUp() {
        token = createToken(WRITER_ID, 30L);
        redisServer = new RedisServer(REDIS_PORT);
        redisServer.start();
    }

    @AfterEach
    void tearDown() {
        redisServer.stop();
    }

    @Test
    void blogTest() {
        createArticle("category name");
        retrieveArticle(1L);
        updateArticle();
        deleteArticle(1L);
    }

    @Test
    void retrieveArticleListTest() {
        for (long categoryId = 1; categoryId <= 10; categoryId++) {
            createArticle(String.valueOf(categoryId));
        }

        retrieveArticleList(0, 10);
    }

    @Test
    void retrieveCategoryListTest() {
        for (long categoryId = 1; categoryId <= 10; categoryId++) {
            createArticle(String.valueOf(categoryId));
        }

        retrieveCategoryList(0, 10);
    }

    private void createArticle(String categoryName) {
        CreateArticleRequest request = createArticleRequest().toBuilder()
                .categoryName(categoryName)
                .build();

        String response = given()
                .log().all()
                .port(port)
                .contentType(APPLICATION_JSON_VALUE)
                .accept(APPLICATION_JSON_VALUE)
                .header(AUTHORIZATION, token)
                .body(request)

                .when()
                .post("/api/articles")

                .then()
                .statusCode(OK.value())
                .assertThat().body("$", x -> hasKey("id"))
                .assertThat().body("$", x -> hasKey("title"))
                .assertThat().body("$", x -> hasKey("content"))
                .assertThat().body("$", x -> hasKey("createdAt"))
                .assertThat().body("$", x -> hasKey("updatedAt"))
                .extract()
                .asString();

        JsonElement element = JsonParser.parseString(response);
        System.out.println(gson.toJson(element));
    }

    private void retrieveArticle(Long id) {
        String response = given()
                .log().all()
                .port(port)
                .accept(APPLICATION_JSON_VALUE)

                .when()
                .get("/api/articles/" + id)

                .then()
                .statusCode(OK.value())
                .assertThat().body("$", x -> hasKey("id"))
                .assertThat().body("$", x -> hasKey("title"))
                .assertThat().body("$", x -> hasKey("content"))
                .assertThat().body("$", x -> hasKey("createdAt"))
                .assertThat().body("$", x -> hasKey("updatedAt"))
                .extract()
                .asString();

        JsonElement element = JsonParser.parseString(response);
        System.out.println(gson.toJson(element));
    }

    private void updateArticle() {
        String response = given()
                .log().all()
                .port(port)
                .accept(APPLICATION_JSON_VALUE)
                .contentType(APPLICATION_JSON_VALUE)
                .header(AUTHORIZATION, token)
                .body(updateArticleRequest())

                .when()
                .put("/api/articles")

                .then()
                .statusCode(OK.value())
                .assertThat().body("$", x -> hasKey("id"))
                .assertThat().body("$", x -> hasKey("title"))
                .assertThat().body("$", x -> hasKey("content"))
                .assertThat().body("$", x -> hasKey("createdAt"))
                .assertThat().body("$", x -> hasKey("updatedAt"))
                .extract()
                .asString();

        JsonElement element = JsonParser.parseString(response);
        System.out.println(gson.toJson(element));
    }

    private void deleteArticle(Long id) {
        String response = given()
                .log().all()
                .port(port)
                .header(AUTHORIZATION, token)

                .when()
                .delete("/api/articles/" + id)

                .then()
                .statusCode(OK.value())
                .extract()
                .asString();

        JsonElement element = JsonParser.parseString(response);
        System.out.println(gson.toJson(element));
    }

    private void retrieveArticleList(int page, int size) {
        String response = given()
                .log().all()
                .port(port)
                .accept(APPLICATION_JSON_VALUE)
                .queryParam("page", page)
                .queryParam("size", size)

                .when()
                .get("/api/articles/query")

                .then()
                .statusCode(OK.value())
                .assertThat().body("$", x -> hasKey("articleResponses"))
                .extract()
                .asString();

        JsonElement element = JsonParser.parseString(response);
        System.out.println(gson.toJson(element));
    }

    private void retrieveCategoryList(int page, int size) {
        String response = given()
                .log().all()
                .port(port)
                .accept(APPLICATION_JSON_VALUE)
                .queryParam("page", page)
                .queryParam("size", size)

                .when()
                .get("/api/categories/query")

                .then()
                .statusCode(OK.value())
                .assertThat().body("$", x -> hasKey("categoryResponses"))
                .extract()
                .asString();

        JsonElement element = JsonParser.parseString(response);
        System.out.println(gson.toJson(element));
    }
}
