package me.jun.blogservice.core.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.jun.blogservice.core.application.dto.*;
import me.jun.blogservice.core.application.exception.ArticleNotFoundException;
import me.jun.blogservice.core.domain.Category;
import me.jun.blogservice.core.domain.repository.ArticleRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;

    private final CategoryService categoryService;

    public Mono<ArticleResponse> createArticle(Mono<CreateArticleRequest> requestMono) {
        return requestMono.map(
                request -> {
                    Category category = categoryService.createCategoryOrElseGet(request.getCategoryName());
                    return request.toEntity()
                            .toBuilder()
                            .categoryId(category.getId())
                            .build();
                        }
                )
                .map(articleRepository::save)
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
        );
    }

    public Mono<Void> deleteArticle(Mono<DeleteArticleRequest> requestMono) {
        return requestMono
                .doOnNext(request -> articleRepository.deleteById(request.getId()))
                .flatMap(request -> Mono.empty());
    }

    public Mono<ArticleListResponse> retrieveArticleList(Mono<PageRequest> requestMono) {
        return requestMono
                .map(request -> articleRepository.findAllBy(request))
                .map(ArticleListResponse::of);
    }
}
