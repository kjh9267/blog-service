package me.jun.blogservice.core.domain;

import lombok.*;

import java.time.Instant;

@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Article {

    private Long id;

    private Long categoryId;

    private Long writerId;

    private String title;

    private String content;

    private Instant createdAt;

    private Instant updatedAt;

    public Article updateTitle(String newTitle) {
        this.title = newTitle;
        return this;
    }

    public Article updateContent(String newContent) {
        this.content = newContent;
        return this;
    }
}
