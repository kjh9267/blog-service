package me.jun.blogservice.core.application.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(toBuilder = true)
@Getter
public class UpdateCategoryRequest {

    @NotNull
    @Positive
    private Long id;

    @NotBlank
    private String name;


}
