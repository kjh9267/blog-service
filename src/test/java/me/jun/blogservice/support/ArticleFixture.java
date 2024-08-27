package me.jun.blogservice.support;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import me.jun.blogservice.core.domain.Article;

import java.time.Instant;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
abstract public class ArticleFixture {

    public static final Long ARTICLE_ID = 1L;

    public static final String TITLE = "title string";

    public static final String CONTENT = "content string";

    public static final Instant CREATED_AT = Instant.now();

    public static final Instant UPDATED_AT = Instant.now();

    public static final Long CATEGORY_ID = 1L;

    public static final Long WRITER_ID = 1L;

    public static final String NEW_TITLE = "new title string";

    public static final String NEW_CONTENT = "new content string";

    public static Article article() {
        return Article.builder()
                .id(ARTICLE_ID)
                .categoryId(CATEGORY_ID)
                .writerId(WRITER_ID)
                .title(TITLE)
                .content(CONTENT)
                .createdAt(CREATED_AT)
                .updatedAt(UPDATED_AT)
                .build();
    }

    public static Article titleUpdatedArticle() {
        return article().toBuilder()
                .title(NEW_TITLE)
                .build();
    }

    public static Article contentUpdatedArticle() {
        return article().toBuilder()
                .content(NEW_CONTENT)
                .build();
    }
}
