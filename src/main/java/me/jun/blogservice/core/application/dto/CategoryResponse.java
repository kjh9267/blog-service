package me.jun.blogservice.core.application.dto;

import lombok.*;
import me.jun.blogservice.core.domain.Category;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(toBuilder = true)
@Getter
public class CategoryResponse {

    private Long id;

    private String name;

    private Long mappedArticleCount;

    public static CategoryResponse of(Category category) {
        return CategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .mappedArticleCount(category.getMappedArticleCount())
                .build();
    }
}
