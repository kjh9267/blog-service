package me.jun.blogservice.core.application.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(toBuilder = true)
@Getter
public class UpdateArticleRequest {

    @NotNull
    @Positive
    private Long id;

    @NotBlank
    private String title;

    @NotBlank
    private String content;

    private Long writerId;
}
