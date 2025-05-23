package me.jun.blogservice.core.application;

import me.jun.blogservice.core.application.dto.ArticleListResponse;
import me.jun.blogservice.core.application.dto.ArticleResponse;
import me.jun.blogservice.core.application.exception.ArticleNotFoundException;
import me.jun.blogservice.core.domain.Article;
import me.jun.blogservice.core.domain.Writer;
import me.jun.blogservice.core.domain.exception.WriterMismatchException;
import me.jun.blogservice.core.domain.repository.ArticleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import reactor.core.publisher.Mono;

import java.util.Optional;

import static me.jun.blogservice.support.ArticleFixture.*;
import static me.jun.blogservice.support.CategoryFixture.category;
import static me.jun.blogservice.support.RedisFixture.ARTICLE_SIZE;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
@SuppressWarnings("deprecation")
public class ArticleServiceTest {

    private ArticleService articleService;

    @Mock
    private ArticleRepository articleRepository;

    @Mock
    private CategoryService categoryService;

    @Mock
    private RedisService redisService;

    @BeforeEach
    void setUp() {
        articleService = new ArticleService(
                articleRepository,
                categoryService,
                redisService,
                ARTICLE_SIZE
        );
    }

    @Test
    void createArticleTest() {
        ArticleResponse expected = articleResponse();

        given(categoryService.createCategoryOrElseGet(any()))
                .willReturn(Mono.just(category()));

        given(articleRepository.save(any()))
                .willReturn(article());

        doNothing()
                .when(redisService)
                .deleteArticleList();

        assertThat(articleService.createArticle(Mono.just(createArticleRequest())).block())
                .isEqualTo(expected);

        verify(redisService).deleteArticleList();
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

        doNothing()
                .when(redisService)
                .deleteArticleList();

        assertThat(articleService.updateArticle(Mono.just(updateArticleRequest())).block())
                .isEqualTo(expected);

        verify(redisService)
                .deleteArticleList();
    }

    @Test
    void noArticle_updateArticleFailTest() {
        given(articleRepository.findById(any()))
                .willReturn(Optional.empty());

        assertThrows(
                ArticleNotFoundException.class,
                () -> articleService.updateArticle(Mono.just(updateArticleRequest())).block()
        );
    }

    @Test
    void invalidWriter_updateArticleFailTest() {
        Article article = article().toBuilder()
                .writer(Writer.builder().value(2L).build())
                .build();

        given(articleRepository.findById(any()))
                .willReturn(Optional.of(article));

        assertThrows(
                WriterMismatchException.class,
                () -> articleService.updateArticle(Mono.just(updateArticleRequest())).block()
        );
    }

    @Test
    void deleteArticleTest() {
        doNothing()
                .when(articleRepository)
                .deleteById(any());

        doNothing()
                .when(redisService)
                .deleteArticleList();

        articleService.deleteArticle(Mono.just(deleteArticleRequest()))
                .block();

        verify(articleRepository)
                .deleteById(deleteArticleRequest().getId());

        verify(redisService)
                .deleteArticleList();
    }

    @Test
    void retrieveArticleListTest() {
        ArticleListResponse expected = articleListResponse();

        given(articleRepository.findAllBy(any()))
                .willReturn(articleList());

        assertThat(articleService.retrieveArticleList(Mono.just(PageRequest.of(0, 10))).block())
                .isEqualToComparingFieldByField(expected);
    }
}
