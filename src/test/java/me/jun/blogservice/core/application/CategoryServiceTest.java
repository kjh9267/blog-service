package me.jun.blogservice.core.application;

import me.jun.blogservice.core.application.dto.CategoryListResponse;
import me.jun.blogservice.core.application.dto.CategoryResponse;
import me.jun.blogservice.core.application.exception.CategoryNotFoundException;
import me.jun.blogservice.core.domain.Category;
import me.jun.blogservice.core.domain.repository.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import reactor.core.publisher.Mono;

import java.util.Optional;

import static me.jun.blogservice.support.CategoryFixture.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
@SuppressWarnings("deprecation")
class CategoryServiceTest {

    private CategoryService categoryService;

    @Mock
    private CategoryRepository categoryRepository;

    @BeforeEach
    void setUp() {
        categoryService = new CategoryService(categoryRepository);
    }

    @Test
    void createCategoryTest() {
        Category expected = initialCategory();

        given(categoryRepository.findByName(any()))
                .willReturn(Optional.empty());

        given(categoryRepository.save(any()))
                .willReturn(initialCategory());

        assertThat(categoryService.createCategoryOrElseGet(Mono.just(CATEGORY_NAME)).block())
                .isEqualToComparingFieldByField(expected);
    }

    @Test
    void getCategoryTest() {
        Category expected = initialCategory();

        given(categoryRepository.findByName(any()))
                .willReturn(Optional.of(initialCategory()));

        assertThat(categoryService.createCategoryOrElseGet(Mono.just(CATEGORY_NAME)).block())
                .isEqualToComparingFieldByField(expected);
    }

    @Test
    void retrieveCategoryByIdTest() {
        CategoryResponse expected = categoryResponse();

        given(categoryRepository.findById(any()))
                .willReturn(Optional.of(initialCategory()));

        assertThat(categoryService.retrieveCategoryById(Mono.just(retrieveCategoryRequest())).block())
                .isEqualToComparingFieldByField(expected);
    }

    @Test
    void noCategory_retrieveCategoryByIdFailTest() {
        given(categoryRepository.findById(any()))
                .willThrow(CategoryNotFoundException.of(String.valueOf(CATEGORY_ID)));

        assertThrows(
                CategoryNotFoundException.class,
                () -> categoryService.retrieveCategoryById(Mono.just(retrieveCategoryRequest())).block()
        );
    }

    @Test
    void retrieveCategoryListTest() {
        CategoryListResponse expected = categoryListResponse();

        given(categoryRepository.findAllBy(any()))
                .willReturn(categoryList());

        assertThat(categoryService.retrieveCategoryList(Mono.just(PageRequest.of(0, 10))).block())
                .isEqualToComparingFieldByField(expected);
    }
}