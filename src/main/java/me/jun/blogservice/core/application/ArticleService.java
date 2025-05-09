package me.jun.blogservice.core.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.jun.blogservice.core.application.dto.*;
import me.jun.blogservice.core.application.exception.ArticleNotFoundException;
import me.jun.blogservice.core.domain.Category;
import me.jun.blogservice.core.domain.Writer;
import me.jun.blogservice.core.domain.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Slf4j
@Service
@Transactional
public class ArticleService {

    private final ArticleRepository articleRepository;

    private final CategoryService categoryService;

    private final RedisService redisServiceImpl;

    private int articleSize;

    public ArticleService(
            ArticleRepository articleRepository,
            CategoryService categoryService,
            RedisService redisService,
            @Value("${article-size}") int articleSize
    ) {
        this.articleRepository = articleRepository;
        this.categoryService = categoryService;
        this.redisServiceImpl = redisService;
        this.articleSize = articleSize;
    }

    public Mono<ArticleResponse> createArticle(Mono<CreateArticleRequest> requestMono) {
        return requestMono.map(
                        request -> {
                            Mono<String> categoryNameMono = Mono.fromSupplier(
                                    () -> request.getCategoryName()
                            ).log();

                            Category category = categoryService.createCategoryOrElseGet(categoryNameMono).block();
                            return request.toEntity()
                                    .toBuilder()
                                    .categoryId(category.getId())
                                    .build();
                        }
                ).log()
                .map(articleRepository::save).log()
                .publishOn(Schedulers.boundedElastic()).log()
                .doOnNext(article -> redisServiceImpl.deleteArticleList()).log()
                .map(ArticleResponse::of);
    }

    public Mono<ArticleResponse> retrieveArticle(Mono<RetrieveArticleRequest> requestMono) {
        return requestMono.map(
                request -> articleRepository.findById(request.getId())
                        .map(ArticleResponse::of)
                        .orElseThrow(() -> ArticleNotFoundException.of(String.valueOf(request.getId())))
        );
    }

    public Mono<ArticleResponse> updateArticle(Mono<UpdateArticleRequest> requestMono) {
        return requestMono.map(
                request -> articleRepository.findById(request.getId())
                        .map(article -> article.validateWriter(request.getWriterId()))
                        .map(article -> article.updateTitle(request.getTitle()))
                        .map(article -> article.updateContent(request.getContent()))
                        .map(ArticleResponse::of)
                        .orElseThrow(() -> ArticleNotFoundException.of(String.valueOf(request.getId())))
        ).log()
                .publishOn(Schedulers.boundedElastic()).log()
                .doOnNext(article -> {
                    if (article.getId() <= articleSize) {
                        redisServiceImpl.deleteArticleList();
                    }
                });
    }

    public Mono<Void> deleteArticle(Mono<DeleteArticleRequest> requestMono) {
        return requestMono
                .doOnNext(request -> articleRepository.deleteById(request.getId())).log()
                .publishOn(Schedulers.boundedElastic()).log()
                .doOnNext(request -> {
                    if (request.getId() <= articleSize) {
                        redisServiceImpl.deleteArticleList();
                    }
                }).log()
                .flatMap(request -> Mono.empty());
    }

    public Mono<ArticleListResponse> retrieveArticleList(Mono<PageRequest> requestMono) {
        return requestMono
                .map(request -> articleRepository.findAllBy(request)).log()
                .map(ArticleListResponse::of);
    }

    @KafkaListener(
            topics = "member.delete",
            groupId = "blog-service"
    )
    public void deleteAllArticleByWriter(
            @Payload Long writerId,
            Acknowledgment acknowledgment
    ) {
        Writer writer = Writer.builder()
                .value(writerId)
                .build();

        articleRepository.deleteAllByWriter(writer);
        acknowledgment.acknowledge();
    }
}
