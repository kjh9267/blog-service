package me.jun.blogservice.core.application.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter
public class DeleteArticleRequest {

    @NotNull
    private Long id;
}