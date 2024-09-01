package me.jun.blogservice.core.application.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(toBuilder = true)
@Getter
public class CreateCategoryRequest {

    @NotBlank
    private String name;
}
