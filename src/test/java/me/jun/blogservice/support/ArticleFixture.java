package me.jun.blogservice.support;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import me.jun.blogservice.core.application.dto.*;
import me.jun.blogservice.core.domain.Article;
import me.jun.blogservice.core.domain.ArticleInfo;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import static me.jun.blogservice.support.CategoryFixture.CATEGORY_NAME;
import static me.jun.blogservice.support.WriterFixture.WRITER_ID;
import static me.jun.blogservice.support.WriterFixture.writer;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
abstract public class ArticleFixture {

    public static final Long ARTICLE_ID = 1L;

    public static final String TITLE = "title string";

    public static final String CONTENT = "content string";

    public static final Instant CREATED_AT = Instant.now();

    public static final Instant UPDATED_AT = Instant.now();

    public static final Long CATEGORY_ID = 1L;

    public static final String NEW_TITLE = "new title string";

    public static final String NEW_CONTENT = "new content string";

    public static Article article() {
        return Article.builder()
                .id(ARTICLE_ID)
                .categoryId(CATEGORY_ID)
                .writer(writer())
                .articleInfo(articleInfo())
                .createdAt(CREATED_AT)
                .updatedAt(UPDATED_AT)
                .build();
    }

    public static ArticleInfo articleInfo() {
        return ArticleInfo.builder()
                .title(TITLE)
                .content(CONTENT)
                .build();
    }

    public static ArticleInfo updatedArticleInfo() {
        return ArticleInfo.builder()
                .title(NEW_TITLE)
                .content(NEW_CONTENT)
                .build();
    }

    public static Article titleUpdatedArticle() {
        return article().toBuilder()
                .articleInfo(
                        articleInfo().toBuilder()
                                .title(NEW_TITLE)
                                .build()
                )
                .build();
    }

    public static Article contentUpdatedArticle() {
        return article().toBuilder()
                .articleInfo(
                        articleInfo().toBuilder()
                                .content(NEW_CONTENT)
                                .build()
                )
                .build();
    }

    public static Article updatedArticle() {
        return article().toBuilder()
                .articleInfo(updatedArticleInfo())
                .build();
    }

    public static CreateArticleRequest createArticleRequest() {
        return CreateArticleRequest.builder()
                .title(TITLE)
                .content(CONTENT)
                .writerId(WRITER_ID)
                .categoryName(CATEGORY_NAME)
                .build();
    }

    public static ArticleResponse articleResponse() {
        return ArticleResponse.builder()
                .id(ARTICLE_ID)
                .title(TITLE)
                .content(CONTENT)
                .createdAt(CREATED_AT)
                .updatedAt(UPDATED_AT)
                .build();
    }

    public static RetrieveArticleRequest retrieveArticleRequest() {
        return RetrieveArticleRequest.builder()
                .id(ARTICLE_ID)
                .build();
    }

    public static UpdateArticleRequest updateArticleRequest() {
        return UpdateArticleRequest.builder()
                .id(ARTICLE_ID)
                .title(NEW_TITLE)
                .content(NEW_CONTENT)
                .writerId(WRITER_ID)
                .build();
    }

    public static ArticleResponse updatedArticleResponse() {
        return articleResponse().toBuilder()
                .title(NEW_TITLE)
                .content(NEW_CONTENT)
                .build();
    }

    public static DeleteArticleRequest deleteArticleRequest() {
        return DeleteArticleRequest.builder()
                .id(ARTICLE_ID)
                .build();
    }

    public static List<Article> articleList() {
        return LongStream.rangeClosed(1, 10)
                .mapToObj(id -> article()
                        .toBuilder()
                        .id(id)
                        .build()
                )
                .collect(Collectors.toList());
    }

    public static ArticleListResponse articleListResponse() {
        return ArticleListResponse.of(articleList());
    }
}
