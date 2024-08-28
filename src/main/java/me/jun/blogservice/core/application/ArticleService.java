package me.jun.blogservice.core.application;

import lombok.RequiredArgsConstructor;
import me.jun.blogservice.core.application.dto.*;
import me.jun.blogservice.core.application.exception.ArticleNotFoundException;
import me.jun.blogservice.core.domain.Article;
import me.jun.blogservice.core.domain.repository.ArticleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;


    public ArticleResponse createArticle(CreateArticleRequest request) {
        Article article = request.toEntity();

        Article savedArticle = articleRepository.save(article);

        return ArticleResponse.of(savedArticle);
    }

    public ArticleResponse retrieveArticle(RetrieveArticleRequest request) {
        Long requestId = request.getId();

        Article article = articleRepository.findById(requestId)
                .orElseThrow(() -> new ArticleNotFoundException(requestId));

        return ArticleResponse.of(article);
    }

    public ArticleResponse updateArticle(UpdateArticleRequest request) {
        Article updatedArticle = articleRepository.findById(request.getId())
                .map(article -> article.updateTitle(request.getTitle()))
                .map(article -> article.updateContent(request.getContent()))
                .orElseThrow(() -> new ArticleNotFoundException(request.getId()));

        return ArticleResponse.of(updatedArticle);
    }

    public void deleteArticle(DeleteArticleRequest request) {
        articleRepository.deleteById(request.getId());
    }
}
