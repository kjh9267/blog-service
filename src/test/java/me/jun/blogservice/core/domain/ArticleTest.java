package me.jun.blogservice.core.domain;

import org.junit.jupiter.api.Test;

import static me.jun.blogservice.support.ArticleFixture.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SuppressWarnings("deprecation")
public class ArticleTest {

    @Test
    void constructorTest() {
        Article expected = new Article();

        assertThat(new Article())
                .isEqualToComparingFieldByField(expected);
    }

    @Test
    void constructor2Test() {
        Article expected = new Article(1L, CATEGORY_ID, WRITER_ID, TITLE, CONTENT, CREATED_AT, UPDATED_AT);

        assertThat(article())
                .isEqualToComparingFieldByField(expected);
    }

    @Test
    void updateTitleTest() {
        Article expected = titleUpdatedArticle();

        assertThat(article().updateTitle("new title string"))
                .isEqualToComparingFieldByField(expected);
    }

    @Test
    void updateContentTest() {
        Article expected = contentUpdatedArticle();

        assertThat(article().updateContent("new content string"))
                .isEqualToComparingFieldByField(expected);
    }
}
