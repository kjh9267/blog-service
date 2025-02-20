package me.jun.blogservice.core.domain.repository;

import me.jun.blogservice.core.domain.Article;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static java.time.Instant.now;
import static me.jun.blogservice.support.ArticleFixture.article;
import static me.jun.blogservice.support.WriterFixture.writer;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DataJpaTest(properties = "spring.cloud.config.enabled=false")
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
    void findAllByTest() {
        int expected = 10;

        for (int count = 0; count < 20; count++) {
            articleRepository.save(
                    article().toBuilder()
                            .id(null)
                            .build()
            );
        }

        List<Article> articles = articleRepository.findAllBy(PageRequest.of(0, 10));

        assertThat(articles.size())
                        .isEqualTo(expected);
    }

    @Test
    @Rollback(value = false)
    void deleteAllByWriterTest() {
        for (long id = 1; id <= 10; id++) {
            Article article = article().toBuilder()
                    .id(id)
                    .build();

            articleRepository.save(article);
        }

        articleRepository.deleteAllByWriter(writer());

        for (long id = 1; id <= 10; id++) {
            assertThat(articleRepository.findById(id))
                    .isEmpty();
        }
    }
}
