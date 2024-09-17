package me.jun.blogservice.core.application.dto;

import lombok.*;
import me.jun.blogservice.core.domain.Article;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter
public class ArticleListResponse {

    private List<ArticleResponse> articleResponses;

    public static ArticleListResponse of(List<Article> articles) {
        List<ArticleResponse> articleResponseList = articles.stream()
                .map(ArticleResponse::of)
                .collect(Collectors.toList());

        return ArticleListResponse.builder()
                .articleResponses(articleResponseList)
                .build();
    }
}
