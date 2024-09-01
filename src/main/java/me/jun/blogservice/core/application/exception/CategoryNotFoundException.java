package me.jun.blogservice.core.application.exception;

import me.jun.blogservice.support.BusinessException;

import static org.springframework.http.HttpStatus.NOT_FOUND;

public class CategoryNotFoundException extends BusinessException {

    private CategoryNotFoundException(String message) {
        super(message);
        status = NOT_FOUND;
    }

    public static CategoryNotFoundException of(String message) {
        return new CategoryNotFoundException(message);
    }
}
