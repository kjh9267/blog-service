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
        return new Article(ARTICLE_ID, CATEGORY_ID, WRITER_ID, TITLE, CONTENT, CREATED_AT, UPDATED_AT);
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
