package me.jun.blogservice.core.domain;

import org.junit.jupiter.api.Test;

import static me.jun.blogservice.support.CategoryFixture.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SuppressWarnings("deprecation")
public class CategoryTest {

    @Test
    void constructorTest() {
        Category expected = new Category();

        assertThat(new Category())
                .isEqualTo(expected);
    }

    @Test
    void constructorTest2() {
        Category expected = Category.builder()
                .id(CATEGORY_ID)
                .name(CATEGORY_NAME)
                .mappedArticleCount(MAPPED_ARTICLE_COUNT)
                .build();

        assertThat(category())
                .isEqualToComparingFieldByField(expected);
    }

    @Test
    void incrementMappedArticleCountTest() {
        Category expected = category()
                .toBuilder()
                .mappedArticleCount(2L)
                .build();

        assertThat(category().incrementMappedArticleCount())
                .isEqualToComparingFieldByField(expected);
    }

    @Test
    void decrementMappedArticleCountTest() {
        Category expected = category()
                .toBuilder()
                .mappedArticleCount(0L)
                .build();

        assertThat(category().decrementMappedArticleCount())
                .isEqualToComparingFieldByField(expected);
    }
}
