package me.jun.blogservice.core.domain.repository;

import me.jun.blogservice.core.domain.Article;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import static java.time.Instant.now;
import static me.jun.blogservice.support.ArticleFixture.article;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DataJpaTest
@ActiveProfiles("test")
@SuppressWarnings("deprecation")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ArticleRepositoryTest {

    @Autowired
    private ArticleRepository articleRepository;

    @Test
    void findByIdTest() {
        Article expected = article().toBuilder()
                .createdAt(now())
                .updatedAt(now())
                .build();

        Article savedArticle = articleRepository.save(
                article().toBuilder()
                        .id(null)
                        .createdAt(null)
                        .updatedAt(null)
                        .build()
        );

        assertAll(
                () -> assertThat(savedArticle)
                        .isEqualToIgnoringGivenFields(expected, "createdAt", "updatedAt"),
                () -> assertThat(savedArticle.getCreatedAt())
                        .isAfterOrEqualTo(expected.getCreatedAt()),
                () -> assertThat(savedArticle.getUpdatedAt())
                        .isAfterOrEqualTo(expected.getUpdatedAt())
        );
    }

    @Test
    void findAllTest() {
        int expected = 10;

        for (int count = 0; count < 20; count++) {
            articleRepository.save(
                    article().toBuilder()
                            .id(null)
                            .build()
            );
        }

        Page<Article> page = articleRepository.findAll(PageRequest.of(0, 10));

        assertThat(page.getSize())
                        .isEqualTo(expected);
    }
}
