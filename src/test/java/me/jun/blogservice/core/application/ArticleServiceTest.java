package me.jun.blogservice.core.application;

import me.jun.blogservice.core.application.dto.ArticleResponse;
import me.jun.blogservice.core.application.exception.ArticleNotFoundException;
import me.jun.blogservice.core.domain.repository.ArticleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

import java.util.Optional;

import static me.jun.blogservice.support.ArticleFixture.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@SuppressWarnings("deprecation")
public class ArticleServiceTest {

    private ArticleService articleService;

    @Mock
    private ArticleRepository articleRepository;

    @BeforeEach
    void setUp() {
        articleService = new ArticleService(articleRepository);
    }

    @Test
    void createArticleTest() {
        ArticleResponse expected = articleResponse();

        given(articleRepository.save(any()))
                .willReturn(article());

        assertThat(articleService.createArticle(Mono.just(createArticleRequest())).block())
                .isEqualTo(expected);
    }

    @Test
    void retrieveArticleTest() {
        ArticleResponse expected = articleResponse();

        given(articleRepository.findById(any()))
                .willReturn(Optional.of(article()));

        assertThat(articleService.retrieveArticle(Mono.just(retrieveArticleRequest())).block())
                .isEqualTo(expected);
    }

    @Test
    void retrieveArticleFailTest() {
        given(articleRepository.findById(any()))
                .willReturn(Optional.empty());

        assertThrows(
                ArticleNotFoundException.class,
                () -> articleService.retrieveArticle(Mono.just(retrieveArticleRequest())).block()
        );
    }

    @Test
    void updateArticleTest() {
        ArticleResponse expected = updatedArticleResponse();

        given(articleRepository.findById(any()))
                .willReturn(Optional.of(updatedArticle()));

        assertThat(articleService.updateArticle(Mono.just(updateArticleRequest())).block())
                .isEqualTo(expected);
    }

    @Test
    void updateArticleFailTest() {
        given(articleRepository.findById(any()))
                .willReturn(Optional.empty());

        assertThrows(
                ArticleNotFoundException.class,
                () -> articleService.updateArticle(Mono.just(updateArticleRequest())).block()
        );
    }

    @Test
    void deleteArticleTest() {
        doNothing()
                .when(articleRepository)
                .deleteById(any());

        articleService.deleteArticle(Mono.just(deleteArticleRequest()))
                .block();

        verify(articleRepository)
                .deleteById(deleteArticleRequest().getId());
    }
}
