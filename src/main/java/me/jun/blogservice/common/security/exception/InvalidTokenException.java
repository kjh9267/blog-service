package me.jun.blogservice.common.security.exception;

import me.jun.blogservice.support.BusinessException;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;

public class InvalidTokenException extends BusinessException {

    private InvalidTokenException(String token) {
        super(token);
        status = UNAUTHORIZED;
    }

    public static InvalidTokenException of(String message) {
        return new InvalidTokenException(message);
    }
}
