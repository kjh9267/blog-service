package me.jun.blogservice.core.application.dto;

import lombok.*;
import me.jun.blogservice.core.domain.Category;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter
public class CategoryListResponse {

    private List<CategoryResponse> categoryResponses;

    public static CategoryListResponse of(List<Category> categories) {
        List<CategoryResponse> categoryResponseList = categories.stream()
                .map(CategoryResponse::of)
                .collect(Collectors.toList());

        return CategoryListResponse.builder()
                .categoryResponses(categoryResponseList)
                .build();
    }
}
