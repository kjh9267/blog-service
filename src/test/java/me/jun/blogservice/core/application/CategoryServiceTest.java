package me.jun.blogservice.core.application;

import me.jun.blogservice.core.application.dto.CategoryResponse;
import me.jun.blogservice.core.application.exception.CategoryNotFoundException;
import me.jun.blogservice.core.domain.repository.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
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
        CategoryResponse expected = categoryResponse();

        given(categoryRepository.save(any()))
                .willReturn(initialCategory());

        assertThat(categoryService.createCategory(Mono.just(createCategoryRequest())).block())
                .isEqualToComparingFieldByField(expected);
    }

    @Test
    void retrieveCategoryTest() {
        CategoryResponse expected = categoryResponse();

        given(categoryRepository.findById(any()))
                .willReturn(Optional.of(initialCategory()));

        assertThat(categoryService.retrieveCategory(Mono.just(retrieveCategoryRequest())).block())
                .isEqualToComparingFieldByField(expected);
    }

    @Test
    void noCategory_retrieveCategoryFailTest() {
        given(categoryRepository.findById(any()))
                .willThrow(CategoryNotFoundException.of(String.valueOf(CATEGORY_ID)));

        assertThrows(
                CategoryNotFoundException.class,
                () -> categoryService.retrieveCategory(Mono.just(retrieveCategoryRequest())).block()
        );
    }
}