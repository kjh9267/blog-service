package me.jun.blogservice.core.application.dto;

import lombok.*;
import me.jun.blogservice.core.domain.Article;

import java.time.Instant;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(toBuilder = true)
@Getter
public class ArticleResponse {

    private Long id;

    private String title;

    private String content;

    private Instant createdAt;

    private Instant updatedAt;

    public static ArticleResponse of(Article article) {
        return ArticleResponse.builder()
                .id(article.getId())
                .title(article.getArticleInfo().getTitle())
                .content(article.getArticleInfo().getContent())
                .createdAt(article.getCreatedAt())
                .updatedAt(article.getUpdatedAt())
                .build();
    }
}
