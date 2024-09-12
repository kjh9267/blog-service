package me.jun.blogservice.core.application.dto;

import lombok.*;
import me.jun.blogservice.core.domain.Article;
import org.springframework.data.domain.Page;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@EqualsAndHashCode
@Getter
public class PagedArticleResponse {

    private Page<ArticleResponse> articlesResponses;

    public static PagedArticleResponse of(Page<Article> articles) {
        return PagedArticleResponse.builder()
                .articlesResponses(articles.map(ArticleResponse::of))
                .build();
    }
}
