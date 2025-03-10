package me.jun.blogservice.core.application.dto;

import lombok.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter
public class RetrieveWriterIdRequest {

    private String email;
}
