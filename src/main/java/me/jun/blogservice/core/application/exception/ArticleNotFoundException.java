package me.jun.blogservice.core.application.exception;

public class ArticleNotFoundException extends RuntimeException {
    public ArticleNotFoundException(Long requestId) {
    }
}
