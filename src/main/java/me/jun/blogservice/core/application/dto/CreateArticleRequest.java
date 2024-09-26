package me.jun.blogservice.core.application.dto;

import javax.validation.constraints.NotBlank;
import lombok.*;
import me.jun.blogservice.core.domain.Article;
import me.jun.blogservice.core.domain.ArticleInfo;
import me.jun.blogservice.core.domain.Writer;

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

    @NotBlank
    private String categoryName;

    private Long writerId;

    public Article toEntity() {
        ArticleInfo articleInfo = ArticleInfo.builder()
                .title(title)
                .content(content)
                .build();

        Writer writer = Writer.builder()
                .value(writerId)
                .build();

        return Article.builder()
                .articleInfo(articleInfo)
                .writer(writer)
                .categoryId(null)
                .build();
    }
}
