package me.jun.blogservice.support;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import me.jun.blogservice.core.application.dto.CategoryResponse;
import me.jun.blogservice.core.application.dto.CreateCategoryRequest;
import me.jun.blogservice.core.application.dto.RetrieveCategoryRequest;
import me.jun.blogservice.core.application.dto.UpdateCategoryRequest;
import me.jun.blogservice.core.domain.Category;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
abstract public class CategoryFixture {

    public static final Long CATEGORY_ID = 1L;

    public static final String CATEGORY_NAME = "category name string";

    public static final String NEW_CATEGORY_NAME = "new category name string";

    public static final Long INITIAL_MAPPED_ARTICLE_COUNT = 0L;

    public static final Long MAPPED_ARTICLE_COUNT = 1L;

    public static Category initialCategory() {
        return Category.builder()
                .id(CATEGORY_ID)
                .name(CATEGORY_NAME)
                .mappedArticleCount(INITIAL_MAPPED_ARTICLE_COUNT)
                .build();
    }

    public static Category category() {
        return Category.builder()
                .id(CATEGORY_ID)
                .name(CATEGORY_NAME)
                .mappedArticleCount(MAPPED_ARTICLE_COUNT)
                .build();
    }

    public static CreateCategoryRequest createCategoryRequest() {
        return CreateCategoryRequest.builder()
                .name(CATEGORY_NAME)
                .build();
    }

    public static CategoryResponse categoryResponse() {
        return CategoryResponse.builder()
                .id(CATEGORY_ID)
                .name(CATEGORY_NAME)
                .mappedArticleCount(INITIAL_MAPPED_ARTICLE_COUNT)
                .build();
    }

    public static RetrieveCategoryRequest retrieveCategoryRequest() {
        return RetrieveCategoryRequest.builder()
                .id(CATEGORY_ID)
                .build();
    }

    public static UpdateCategoryRequest updateCategoryRequest() {
        return UpdateCategoryRequest.builder()
                .id(category().getId())
                .name(NEW_CATEGORY_NAME)
                .build();
    }
}
