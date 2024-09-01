package me.jun.blogservice.core.domain;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder(toBuilder = true)
@EqualsAndHashCode(of = "id")
@Getter
@ToString
@Entity
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Long mappedArticleCount;

    public Category incrementMappedArticleCount() {
        this.mappedArticleCount += 1;
        return this;
    }

    public Category decrementMappedArticleCount() {
        this.mappedArticleCount -= 1;
        return this;
    }

    public static Category of(String name) {
        return Category.builder()
                .name(name)
                .mappedArticleCount(0L)
                .build();
    }
}
