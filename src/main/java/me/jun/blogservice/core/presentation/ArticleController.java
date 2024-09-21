package me.jun.blogservice.core.presentation;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.jun.blogservice.common.security.WriterId;
import me.jun.blogservice.core.application.ArticleService;
import me.jun.blogservice.core.application.dto.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static reactor.core.scheduler.Schedulers.boundedElastic;

@Slf4j
@RestController
@RequestMapping("/api/articles")
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;

    @PostMapping(
            consumes = APPLICATION_JSON_VALUE,
            produces = APPLICATION_JSON_VALUE
    )
    public Mono<ResponseEntity<ArticleResponse>> createArticle(@RequestBody @Valid CreateArticleRequest request, @WriterId Long writerId) {
        Mono<CreateArticleRequest> requestMono = Mono.fromSupplier(
                        () -> request.toBuilder()
                                .writerId(writerId)
                                .build()
                ).log()
                .publishOn(boundedElastic()).log();

        return articleService.createArticle(requestMono).log()
                .map(articleResponse -> ResponseEntity.ok()
                        .body(articleResponse)
                ).log()
                .doOnError(throwable -> log.error(throwable.getMessage()));
    }

    @GetMapping(
            value = "/{articleId}",
            produces = APPLICATION_JSON_VALUE
    )
    public Mono<ResponseEntity<ArticleResponse>> retrieveArticle(@PathVariable Long articleId) {
        Mono<RetrieveArticleRequest> requestMono = Mono.fromSupplier(
                () -> RetrieveArticleRequest.of(articleId)
                ).log()
                .publishOn(boundedElastic()).log();

        return articleService.retrieveArticle(requestMono).log()
                .map(articleResponse -> ResponseEntity.ok()
                        .body(articleResponse)
                ).log()
                .doOnError(throwable -> log.error(throwable.getMessage()));
    }

    @PutMapping(
            produces = APPLICATION_JSON_VALUE,
            consumes = APPLICATION_JSON_VALUE
    )
    public Mono<ResponseEntity<ArticleResponse>> updateArticle(@RequestBody @Valid UpdateArticleRequest request, @WriterId Long writerId) {
        Mono<UpdateArticleRequest> requestMono = Mono.fromSupplier(
                        () -> request.toBuilder()
                                .writerId(writerId)
                                .build()
                ).log()
                .publishOn(boundedElastic()).log();

        return articleService.updateArticle(requestMono).log()
                .map(articleResponse -> ResponseEntity.ok()
                        .body(articleResponse)
                ).log()
                .doOnError(throwable -> log.error(throwable.getMessage()));
    }

    @DeleteMapping(value = "/{articleId}")
    public Mono<ResponseEntity<Void>> deleteArticle(@PathVariable Long articleId, @WriterId Long writerId) {
        Mono<DeleteArticleRequest> requestMono = Mono.fromSupplier(
                () -> DeleteArticleRequest.of(articleId)
                ).log()
                .publishOn(boundedElastic()).log();

        return articleService.deleteArticle(requestMono).log()
                .map(empty -> ResponseEntity.ok()
                        .body(empty)
                ).log()
                .doOnError(throwable -> log.error(throwable.getMessage()));
    }

    @GetMapping(
            value = "/query",
            produces = APPLICATION_JSON_VALUE
    )
    public Mono<ResponseEntity<ArticleListResponse>> retrieveArticleList(
            @RequestParam("page") int page,
            @RequestParam("size") int size
    ) {
        Mono<PageRequest> requestMono = Mono.fromSupplier(
                () -> PageRequest.of(page, size)
                ).log()
                .publishOn(boundedElastic()).log();

        return articleService.retrieveArticleList(requestMono).log()
                .map(response -> ResponseEntity.ok()
                        .body(response)
                ).log()
                .doOnError(throwable -> log.error(throwable.getMessage()));
    }
}
