package me.jun.blogservice.core.application;

import me.jun.blogservice.core.domain.Article;
import me.jun.blogservice.core.domain.repository.ArticleRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.context.ActiveProfiles;

import static me.jun.blogservice.support.ArticleFixture.article;
import static me.jun.blogservice.support.WriterFixture.WRITER_ID;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ActiveProfiles("test")
@SpringBootTest
@EmbeddedKafka(
        topics = "member.delete",
        ports = 9092,
        brokerProperties = "listeners=PLAINTEXT://localhost:9092"
)
public class ArticleServiceKafkaTest {

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Value("#{${delete-member-kafka-topic}}")
    private String topic;

    @Test
    void deleteAllArticleByWriterTest() throws InterruptedException {
        for (long id = 1; id <= 10; id++) {
            Article article = article().toBuilder()
                    .id(id)
                    .build();

            articleRepository.save(article);
        }

        kafkaTemplate.send(topic, WRITER_ID.toString());

        Thread.sleep(2_000);

        for (long id = 1; id <= 10; id++) {
            assertThat(articleRepository.findById(id))
                    .isEmpty();
        }
    }
}
