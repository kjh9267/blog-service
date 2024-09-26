package me.jun.blogservice.core.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(toBuilder = true)
@Getter
@EqualsAndHashCode
@Embeddable
public class ArticleInfo {

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "LONGTEXT")
    private String content;

    public ArticleInfo updateTitle(String newTitle) {
        this.title = newTitle;
        return this;
    }

    public ArticleInfo updateContent(String newContent) {
        this.content = newContent;
        return this;
    }
}
