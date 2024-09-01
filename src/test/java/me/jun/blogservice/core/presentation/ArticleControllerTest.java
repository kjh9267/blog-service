package me.jun.blogservice.core.presentation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.jun.blogservice.common.security.JwtProvider;
import me.jun.blogservice.common.security.exception.InvalidTokenException;
import me.jun.blogservice.core.application.ArticleService;
import me.jun.blogservice.core.application.WriterService;
import me.jun.blogservice.core.application.exception.ArticleNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import static me.jun.blogservice.support.ArticleFixture.*;
import static me.jun.blogservice.support.TokenFixture.EMAIL;
import static me.jun.blogservice.support.TokenFixture.TOKEN;
import static me.jun.blogservice.support.WriterFixture.WRITER_EMAIL;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureWebTestClient
public class ArticleControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private ArticleService articleService;

    @MockBean
    private JwtProvider jwtProvider;

    @MockBean
    private WriterService writerServiceImpl;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createArticleTest() throws JsonProcessingException {
        String content = objectMapper.writeValueAsString(createArticleRequest());

        given(jwtProvider.extractSubject(any()))
                .willReturn(WRITER_EMAIL);

        given(writerServiceImpl.retrieveWriterIdByEmail(any()))
                .willReturn(Mono.just(WRITER_ID));

        given(articleService.createArticle(any()))
                .willReturn(Mono.just(articleResponse()));

        webTestClient.post()
                .uri("/api/articles")
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON)
                .header(AUTHORIZATION, TOKEN)
                .bodyValue(content)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody()
                .jsonPath("id").exists()
                .jsonPath("title").exists()
                .jsonPath("content").exists()
                .jsonPath("createdAt").exists()
                .jsonPath("updatedAt").exists()
                .consumeWith(System.out::println);
    }

    @Test
    void noContent_createArticleFailTest() {
        webTestClient.post()
                .uri("/api/articles")
                .header(AUTHORIZATION, TOKEN)
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON)
                .exchange()
                .expectStatus().is4xxClientError()
                .expectBody()
                .jsonPath("detail").exists()
                .consumeWith(System.out::println);
    }

    @Test
    void noToken_createArticleFailTest() throws JsonProcessingException {
        String content = objectMapper.writeValueAsString(createArticleRequest());

        webTestClient.post()
                .uri("/api/articles")
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .bodyValue(content)
                .exchange()
                .expectStatus().is4xxClientError()
                .expectBody()
                .jsonPath("detail").exists()
                .consumeWith(System.out::println);
    }

    @Test
    void invalidToken_createArticleFailTest() throws JsonProcessingException {
        String content = objectMapper.writeValueAsString(createArticleRequest());

        given(jwtProvider.extractSubject(any()))
                .willThrow(InvalidTokenException.of(TOKEN));

        webTestClient.post()
                .uri("/api/articles")
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON)
                .bodyValue(content)
                .exchange()
                .expectStatus().is4xxClientError()
                .expectBody()
                .jsonPath("detail").exists()
                .consumeWith(System.out::println);
    }

    @Test
    void unknownWriter_createArticleFailTest() throws JsonProcessingException {
        String content = objectMapper.writeValueAsString(createArticleRequest());

        given(jwtProvider.extractSubject(any()))
                .willReturn(WRITER_EMAIL);

        given(writerServiceImpl.retrieveWriterIdByEmail(any()))
                .willThrow(WebClientResponseException.class);

        webTestClient.post()
                .uri("/api/articles")
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON)
                .header(AUTHORIZATION, TOKEN)
                .bodyValue(content)
                .exchange()
                .expectStatus().is5xxServerError()
                .expectBody()
                .jsonPath("detail").exists()
                .consumeWith(System.out::println);
    }

    @Test
    void retrieveArticleTest() {
        given(jwtProvider.extractSubject(any()))
                .willReturn(EMAIL);

        given(writerServiceImpl.retrieveWriterIdByEmail(any()))
                .willReturn(Mono.just(WRITER_ID));

        given(articleService.retrieveArticle(any()))
                .willReturn(Mono.just(articleResponse()));

        webTestClient.get()
                .uri("/api/articles/1")
                .accept(APPLICATION_JSON)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody()
                .jsonPath("id").exists()
                .jsonPath("title").exists()
                .jsonPath("content").exists()
                .jsonPath("createdAt").exists()
                .jsonPath("updatedAt").exists()
                .consumeWith(System.out::println);
    }

    @Test
    void wrongPathVariable_retrieveArticleFailTest() {
        webTestClient.get()
                .uri("/api/articles/adsf")
                .accept(APPLICATION_JSON)
                .exchange()
                .expectStatus().is4xxClientError()
                .expectBody()
                .jsonPath("detail").exists()
                .consumeWith(System.out::println);
    }

    @Test
    void noContent_retrieveArticleFailTest() {
        given(articleService.retrieveArticle(any()))
                .willThrow(ArticleNotFoundException.of(String.valueOf(ARTICLE_ID)));

        webTestClient.get()
                .uri("/api/articles/1")
                .accept(APPLICATION_JSON)
                .exchange()
                .expectStatus().is4xxClientError()
                .expectBody()
                .jsonPath("detail").exists()
                .consumeWith(System.out::println);
    }

    @Test
    void updateArticleTest() throws JsonProcessingException {
        String content = objectMapper.writeValueAsString(updateArticleRequest());

        given(jwtProvider.extractSubject(any()))
                .willReturn(EMAIL);

        given(writerServiceImpl.retrieveWriterIdByEmail(any()))
                .willReturn(Mono.just(WRITER_ID));

        given(articleService.updateArticle(any()))
                .willReturn(Mono.just(articleResponse()));

        webTestClient.put()
                .uri("/api/articles")
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON)
                .header(AUTHORIZATION, TOKEN)
                .bodyValue(content)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody()
                .jsonPath("id").exists()
                .jsonPath("title").exists()
                .jsonPath("content").exists()
                .jsonPath("createdAt").exists()
                .jsonPath("updatedAt").exists()
                .consumeWith(System.out::println);
    }

    @Test
    void noToken_updateArticleFailTest() throws JsonProcessingException {
        String content = objectMapper.writeValueAsString(updateArticleRequest());

        webTestClient.put()
                .uri("/api/articles")
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON)
                .bodyValue(content)
                .exchange()
                .expectStatus().is4xxClientError()
                .expectBody()
                .jsonPath("detail").exists()
                .consumeWith(System.out::println);
    }

    @Test
    void invalidToken_updateArticleFailTest() throws JsonProcessingException {
        String content = objectMapper.writeValueAsString(updateArticleRequest());

        given(jwtProvider.extractSubject(any()))
                .willThrow(InvalidTokenException.of(TOKEN));

        webTestClient.put()
                .uri("/api/articles")
                .header(AUTHORIZATION, TOKEN)
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON)
                .bodyValue(content)
                .exchange()
                .expectStatus().is4xxClientError()
                .expectBody()
                .jsonPath("detail").exists()
                .consumeWith(System.out::println);
    }

    @Test
    void unknownWriter_updateArticleFailTest() throws JsonProcessingException {
        String content = objectMapper.writeValueAsString(updateArticleRequest());

        given(jwtProvider.extractSubject(any()))
                .willReturn(EMAIL);

        given(writerServiceImpl.retrieveWriterIdByEmail(any()))
                .willThrow(WebClientResponseException.class);

        webTestClient.put()
                .uri("/api/articles")
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON)
                .header(AUTHORIZATION, TOKEN)
                .bodyValue(content)
                .exchange()
                .expectStatus().is5xxServerError()
                .expectBody()
                .jsonPath("detail").exists()
                .consumeWith(System.out::println);
    }
}
