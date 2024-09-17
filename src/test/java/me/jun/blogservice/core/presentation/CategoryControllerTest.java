package me.jun.blogservice.core.presentation;

import me.jun.blogservice.core.application.CategoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static me.jun.blogservice.support.CategoryFixture.categoryListResponse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureWebTestClient
public class CategoryControllerTest {

    @Autowired
    private WebTestClient webClient;

    @MockBean
    private CategoryService categoryService;

    @Test
    void retrieveCategoryListTest() {
        given(categoryService.retrieveCategoryList(any()))
                .willReturn(Mono.just(categoryListResponse()));

        webClient.get()
                .uri("/api/categories/query?page=0&size=10")
                .accept(APPLICATION_JSON)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody()
                .jsonPath("categoryResponses").exists()
                .consumeWith(System.out::println);
    }
}
