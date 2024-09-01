package me.jun.blogservice.support;

import lombok.Getter;
import org.springframework.http.HttpStatusCode;

@Getter
abstract public class BusinessException extends RuntimeException {

    protected HttpStatusCode status;

    protected BusinessException(String message) {
        super(message);
    }
}
