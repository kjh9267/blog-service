package me.jun.blogservice.core.domain.repository;

import me.jun.blogservice.core.domain.Article;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static java.time.Instant.now;
import static me.jun.blogservice.support.ArticleFixture.article;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DataJpaTest
@ActiveProfiles("test")
@SuppressWarnings("deprecation")
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
}
