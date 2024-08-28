package me.jun.blogservice.core.application.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter
public class RetrieveArticleRequest {

    @NotNull
    @Positive
    private Long id;

    public static RetrieveArticleRequest of(Long requestId) {
        return RetrieveArticleRequest.builder()
                .id(requestId)
                .build();
    }
}
