package me.jun.blogservice.support;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
abstract public class BusinessException extends RuntimeException {

    protected HttpStatus status;

    protected BusinessException(String message) {
        super(message);
    }
}
