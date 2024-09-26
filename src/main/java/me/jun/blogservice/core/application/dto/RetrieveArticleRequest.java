package me.jun.blogservice.core.application.dto;

import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

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
