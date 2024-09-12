package me.jun.blogservice.core.domain.exception;

import me.jun.blogservice.support.BusinessException;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;

public class WriterMismatchException extends BusinessException {

    private WriterMismatchException(String message) {
        super(message);
        status = UNAUTHORIZED;
    }

    public static WriterMismatchException of(String message) {
        return new WriterMismatchException(message);
    }
}
