package me.jun.blogservice.core.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.jun.blogservice.core.application.dto.*;
import me.jun.blogservice.core.application.exception.ArticleNotFoundException;
import me.jun.blogservice.core.domain.repository.ArticleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;


    public Mono<ArticleResponse> createArticle(Mono<CreateArticleRequest> requestMono) {
        return requestMono.log()
                .map(CreateArticleRequest::toEntity)
                .map(articleRepository::save)
                .map(ArticleResponse::of)
                .doOnError(throwable -> log.info("{}", throwable));
    }

    public Mono<ArticleResponse> retrieveArticle(Mono<RetrieveArticleRequest> requestMono) {
        return requestMono.log()
                .map(
                        request -> articleRepository.findById(request.getId())
                                .map(ArticleResponse::of)
                                .orElseThrow(() -> ArticleNotFoundException.of(String.valueOf(request.getId())))
                )
                .doOnError(throwable -> log.info("{}", throwable));
    }

    public Mono<ArticleResponse> updateArticle(Mono<UpdateArticleRequest> requestMono) {
        return requestMono.log()
                .map(
                        request -> articleRepository.findById(request.getId())
                                .map(article -> article.validateWriter(request.getWriterId()))
                                .map(article -> article.updateTitle(request.getTitle()))
                                .map(article -> article.updateContent(request.getContent()))
                                .map(ArticleResponse::of)
                                .orElseThrow(() -> ArticleNotFoundException.of(String.valueOf(request.getId())))
        )
                .doOnError(throwable -> log.info("{}", throwable));
    }

    public Mono<Void> deleteArticle(Mono<DeleteArticleRequest> requestMono) {
        return requestMono.log()
                .doOnNext(request -> articleRepository.deleteById(request.getId()))
                .doOnError(throwable -> log.info("{}", throwable))
                .flatMap(request -> Mono.empty());
    }
}
