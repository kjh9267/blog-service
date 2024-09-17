package me.jun.blogservice.core.domain.repository;

import me.jun.blogservice.core.domain.Category;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static me.jun.blogservice.support.CategoryFixture.CATEGORY_NAME;
import static me.jun.blogservice.support.CategoryFixture.category;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@ActiveProfiles("test")
@SuppressWarnings("deprecation")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    void findByIdTest() {
        Category expected = category();

        Category category = category().toBuilder()
                .id(null)
                .build();

        categoryRepository.save(category);

        assertThat(categoryRepository.findById(1L).get())
                .isEqualToComparingFieldByField(expected);
    }

    @Test
    void findByNameTest() {
        Category expected = category();

        categoryRepository.save(
                category().toBuilder()
                        .id(null)
                        .build()
        );

        assertThat(categoryRepository.findByName(CATEGORY_NAME).get())
                .isEqualToComparingFieldByField(expected);
    }

    @Test
    void findAllByTest() {
        int expected = 10;

        for (int count = 0; count < 20; count++) {
            categoryRepository.save(
                    category().toBuilder()
                            .id(null)
                            .build()
            );
        }

        List<Category> categories = categoryRepository.findAllBy(PageRequest.of(0, 10));

        assertThat(categories.size())
                .isEqualTo(expected);
    }
}
