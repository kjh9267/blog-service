package me.jun.blogservice.core.application;

import me.jun.blogservice.core.application.dto.CategoryResponse;
import me.jun.blogservice.core.domain.repository.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

import static me.jun.blogservice.support.CategoryFixture.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
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
}