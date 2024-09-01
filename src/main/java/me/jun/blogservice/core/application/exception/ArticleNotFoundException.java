package me.jun.blogservice.core.application.exception;

import me.jun.blogservice.support.BusinessException;

import static org.springframework.http.HttpStatus.NOT_FOUND;

public class ArticleNotFoundException extends BusinessException {
    private ArticleNotFoundException(String message) {
        super(message);
        status = NOT_FOUND;
    }

    public static ArticleNotFoundException of(String message) {
        return new ArticleNotFoundException(message);
    }
}
