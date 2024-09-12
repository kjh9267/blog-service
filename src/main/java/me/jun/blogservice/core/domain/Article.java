package me.jun.blogservice.core.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;

import static jakarta.persistence.GenerationType.IDENTITY;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(toBuilder = true)
@EntityListeners(AuditingEntityListener.class)
@EqualsAndHashCode(of = "id")
@ToString
@Getter
@Entity
public class Article {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long categoryId;

    @Embedded
    private Writer writer;

    @Embedded
    private ArticleInfo articleInfo;

    @Column(nullable = false, updatable = false)
    @CreatedDate
    private Instant createdAt;

    @Column(nullable = false)
    @LastModifiedDate
    private Instant updatedAt;

    public Article updateTitle(String newTitle) {
        this.articleInfo = articleInfo.updateTitle(newTitle);
        return this;
    }

    public Article updateContent(String newContent) {
        this.articleInfo = articleInfo.updateContent(newContent);
        return this;
    }
}
