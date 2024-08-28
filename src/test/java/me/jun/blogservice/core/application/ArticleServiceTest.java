package me.jun.blogservice.core.application;

import me.jun.blogservice.core.application.dto.ArticleResponse;
import me.jun.blogservice.core.application.exception.ArticleNotFoundException;
import me.jun.blogservice.core.domain.repository.ArticleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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

        assertThat(articleService.createArticle(createArticleRequest()))
                .isEqualToComparingFieldByField(expected);
    }

    @Test
    void retrieveArticleTest() {
        ArticleResponse expected = articleResponse();

        given(articleRepository.findById(any()))
                .willReturn(Optional.of(article()));

        assertThat(articleService.retrieveArticle(retrieveArticleRequest()))
                .isEqualToComparingFieldByField(expected);
    }

    @Test
    void retrieveArticleFailTest() {
        given(articleRepository.findById(any()))
                .willThrow(ArticleNotFoundException.class);

        assertThrows(
                ArticleNotFoundException.class,
                () -> articleService.retrieveArticle(retrieveArticleRequest())
        );
    }

    @Test
    void updateArticleTest() {
        ArticleResponse expected = updatedArticleResponse();

        given(articleRepository.findById(any()))
                .willReturn(Optional.of(updatedArticle()));

        assertThat(articleService.updateArticle(updateArticleRequest()))
                .isEqualToComparingFieldByField(expected);
    }

    @Test
    void updateArticleFailTest() {
        given(articleRepository.findById(any()))
                .willThrow(ArticleNotFoundException.class);

        assertThrows(
                ArticleNotFoundException.class,
                () -> articleService.updateArticle(updateArticleRequest())
        );
    }

    @Test
    void deleteArticleTest() {
        doNothing()
                .when(articleRepository)
                .deleteById(any());

        articleService.deleteArticle(deleteArticleRequest());

        verify(articleRepository)
                .deleteById(deleteArticleRequest().getId());
    }
}
