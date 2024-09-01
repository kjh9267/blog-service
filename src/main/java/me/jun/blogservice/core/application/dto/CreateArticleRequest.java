package me.jun.blogservice.core.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;
import me.jun.blogservice.core.domain.Article;
import me.jun.blogservice.core.domain.ArticleInfo;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode
@Builder(toBuilder = true)
@Getter
public class CreateArticleRequest {

    @NotBlank
    private String title;

    @NotBlank
    private String content;

    @NotNull
    @Positive
    private Long writerId;

    public Article toEntity() {
        ArticleInfo articleInfo = ArticleInfo.builder()
                .title(title)
                .content(content)
                .build();

        return Article.builder()
                .articleInfo(articleInfo)
                .writerId(writerId)
                .build();
    }
}
