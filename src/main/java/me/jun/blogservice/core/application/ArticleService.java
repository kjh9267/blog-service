package me.jun.blogservice.core.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.jun.blogservice.core.application.dto.*;
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
        return requestMono.map(CreateArticleRequest::toEntity)
                .map(articleRepository::save)
                .map(ArticleResponse::of);
    }

    public Mono<ArticleResponse> retrieveArticle(Mono<RetrieveArticleRequest> requestMono) {
        return requestMono.map(
                request -> articleRepository.findById(request.getId())
                        .map(ArticleResponse::of)
                        .orElse(ArticleResponse.builder().build())
                );
    }

    public Mono<ArticleResponse> updateArticle(Mono<UpdateArticleRequest> requestMono) {
        return requestMono.map(
                request -> articleRepository.findById(request.getId())
                        .map(article -> article.updateTitle(request.getTitle()))
                        .map(article -> article.updateContent(request.getContent()))
                        .map(ArticleResponse::of)
                        .orElse(ArticleResponse.builder().build())
        );
    }

    public Mono<Void> deleteArticle(Mono<DeleteArticleRequest> requestMono) {
        return requestMono.doOnNext(request -> articleRepository.deleteById(request.getId()))
                .flatMap(request -> Mono.empty());
    }
}
