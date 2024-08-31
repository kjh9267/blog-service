package me.jun.blogservice.common.security.exception;

public class InvalidTokenException extends RuntimeException {

    public InvalidTokenException(String token) {
        super(token);
    }
}
