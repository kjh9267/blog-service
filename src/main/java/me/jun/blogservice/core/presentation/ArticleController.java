package me.jun.blogservice.core.presentation;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.jun.blogservice.common.security.WriterId;
import me.jun.blogservice.core.application.ArticleService;
import me.jun.blogservice.core.application.dto.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

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
        return articleService.createArticle(
                Mono.fromSupplier(
                        () -> request.toBuilder()
                                .writerId(writerId)
                                .build()
                )
                        .log()
        )
                .log()
                .map(articleResponse -> ResponseEntity.ok()
                        .body(articleResponse)
                )
                .doOnError(throwable -> log.info("{}", throwable));
    }

    @GetMapping(
            value = "/{articleId}",
            produces = APPLICATION_JSON_VALUE
    )
    public Mono<ResponseEntity<ArticleResponse>> retrieveArticle(@PathVariable Long articleId) {
        return articleService.retrieveArticle(
                Mono.fromSupplier(() -> RetrieveArticleRequest.of(articleId))
                        .log()
                        .doOnError(throwable -> log.info("{}", throwable))
        )
                .log()
                .map(articleResponse -> ResponseEntity.ok()
                        .body(articleResponse)
                )
                .doOnError(throwable -> log.info("{}", throwable));
    }

    @PutMapping(
            produces = APPLICATION_JSON_VALUE,
            consumes = APPLICATION_JSON_VALUE
    )
    public Mono<ResponseEntity<ArticleResponse>> updateArticle(@RequestBody @Valid UpdateArticleRequest request, @WriterId Long writerId) {
        return articleService.updateArticle(
                Mono.fromSupplier(
                        () -> request.toBuilder()
                                .writerId(writerId)
                                .build()
                        )
                        .log()
        )
                .log()
                .map(articleResponse -> ResponseEntity.ok()
                        .body(articleResponse))
                .doOnError(throwable -> log.info("{}", throwable));
    }

    @DeleteMapping(value = "/{articleId}")
    public Mono<ResponseEntity<Void>> deleteArticle(@PathVariable Long articleId, @WriterId Long writerId) {
        return articleService.deleteArticle(
                Mono.fromSupplier(() -> DeleteArticleRequest.of(articleId))
                        .log()
        )
                .log()
                .map(empty -> ResponseEntity.ok()
                        .body(empty))
                .doOnError(throwable -> log.info("{}", throwable));
    }
}
