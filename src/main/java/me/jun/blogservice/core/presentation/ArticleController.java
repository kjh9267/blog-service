package me.jun.blogservice.core.presentation;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.jun.blogservice.common.security.WriterId;
import me.jun.blogservice.core.application.ArticleService;
import me.jun.blogservice.core.application.dto.ArticleResponse;
import me.jun.blogservice.core.application.dto.CreateArticleRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
}
