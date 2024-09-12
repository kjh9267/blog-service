package me.jun.blogservice.core.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static me.jun.blogservice.support.ArticleFixture.*;
import static me.jun.blogservice.support.WriterFixture.writer;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
@SuppressWarnings("deprecation")
public class ArticleTest {

    private Article article;

    @Mock
    private ArticleInfo articleInfo;

    @BeforeEach
    void setUp() {
        article = article().toBuilder()
                .articleInfo(articleInfo)
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

        given(articleInfo.updateTitle(any()))
                .willReturn(updatedArticleInfo());

        ArticleInfo updatedArticleInfo = article.updateTitle("new title string")
                .getArticleInfo();

        assertThat(updatedArticleInfo)
                .isEqualToComparingFieldByField(expected);
    }

    @Test
    void updateContentTest() {
        ArticleInfo expected = updatedArticleInfo();

        given(articleInfo.updateContent(any()))
                .willReturn(updatedArticleInfo());

        ArticleInfo updatedArticleInfo = article.updateContent("new content string")
                .getArticleInfo();

        assertThat(updatedArticleInfo)
                .isEqualToComparingFieldByField(expected);
    }
}
