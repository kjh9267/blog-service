package me.jun.blogservice.core.domain;

import me.jun.blogservice.core.domain.exception.WriterMismatchException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static me.jun.blogservice.support.ArticleFixture.*;
import static me.jun.blogservice.support.WriterFixture.writer;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@SuppressWarnings("deprecation")
public class ArticleTest {

    private Article article;

    @Mock
    private ArticleInfo mockArticleInfo;

    @Mock
    private Writer mockWriter;

    @BeforeEach
    void setUp() {
        article = article().toBuilder()
                .articleInfo(mockArticleInfo)
                .writer(mockWriter)
                .build();
    }

    @Test
    void constructorTest() {
        Article expected = new Article();

        assertThat(new Article())
                .isEqualToComparingFieldByField(expected);
    }

    @Test
    void constructor2Test() {
        Article expected = Article.builder()
                .id(ARTICLE_ID)
                .categoryId(CATEGORY_ID)
                .writer(writer())
                .articleInfo(articleInfo())
                .createdAt(CREATED_AT)
                .updatedAt(UPDATED_AT)
                .build();

        assertThat(article())
                .isEqualToComparingFieldByField(expected);
    }

    @Test
    void updateTitleTest() {
        ArticleInfo expected = updatedArticleInfo();

        given(mockArticleInfo.updateTitle(any()))
                .willReturn(updatedArticleInfo());

        ArticleInfo updatedArticleInfo = article.updateTitle("new title string")
                .getArticleInfo();

        assertThat(updatedArticleInfo)
                .isEqualToComparingFieldByField(expected);
    }

    @Test
    void updateContentTest() {
        ArticleInfo expected = updatedArticleInfo();

        given(mockArticleInfo.updateContent(any()))
                .willReturn(updatedArticleInfo());

        ArticleInfo updatedArticleInfo = article.updateContent("new content string")
                .getArticleInfo();

        assertThat(updatedArticleInfo)
                .isEqualToComparingFieldByField(expected);
    }

    @Test
    void validateWriterTest() {
        given(mockWriter.validate(any()))
                .willReturn(mockWriter);

        assertAll(
                () -> assertDoesNotThrow(
                        () -> article.validateWriter(WRITER_ID)
                ),
                () -> verify(mockWriter)
                        .validate(WRITER_ID)
        );
    }

    @Test
    void validateWriterFailTest() {
        given(mockWriter.validate(any()))
                .willThrow(WriterMismatchException.of("2"));

        assertThrows(
                WriterMismatchException.class,
                () -> article.validateWriter(2L)
        );
    }
}
