package me.jun.blogservice.core.domain;

import org.junit.jupiter.api.Test;

import static me.jun.blogservice.support.ArticleFixture.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings("deprecation")
class ArticleInfoTest {

    @Test
    void constructorTest() {
        ArticleInfo expected = new ArticleInfo();

        assertThat(new ArticleInfo())
                .isEqualTo(expected);
    }

    @Test
    void constructorTest2() {
        ArticleInfo expected = ArticleInfo.builder()
                .title(TITLE)
                .content(CONTENT)
                .build();

        assertThat(articleInfo())
                .isEqualToComparingFieldByField(expected);
    }

    @Test
    void updateTitleTest() {
        ArticleInfo newArticleInfo = articleInfo().toBuilder()
                .title(NEW_TITLE)
                .build();

        Article expected = article().toBuilder()
                .articleInfo(newArticleInfo)
                .build();

        assertThat(article().updateTitle(NEW_TITLE))
                .isEqualToComparingFieldByField(expected);
    }

    @Test
    void updateContentTest() {
        ArticleInfo newArticleInfo = articleInfo().toBuilder()
                .content(NEW_CONTENT)
                .build();

        Article expected = article().toBuilder()
                .articleInfo(newArticleInfo)
                .build();

        assertThat(article().updateContent(NEW_CONTENT))
                .isEqualToComparingFieldByField(expected);
    }
}