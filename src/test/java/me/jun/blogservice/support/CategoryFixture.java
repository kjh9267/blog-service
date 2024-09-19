package me.jun.blogservice.support;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import me.jun.blogservice.core.application.dto.*;
import me.jun.blogservice.core.domain.Category;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
abstract public class CategoryFixture {

    public static final Long CATEGORY_ID = 1L;

    public static final String CATEGORY_NAME = "category name string";

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

    public static List<Category> categoryList() {
        return LongStream.rangeClosed(1, 10)
                .mapToObj(
                        id -> category()
                                .toBuilder()
                                .id(id)
                                .build()
                )
                .collect(Collectors.toList());
    }

    public static CategoryListResponse categoryListResponse() {
        return CategoryListResponse.of(categoryList());
    }
}
