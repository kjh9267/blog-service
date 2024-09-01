package me.jun.blogservice.common.exceptionhandler;

import me.jun.blogservice.support.BusinessException;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebInputException;
import reactor.core.publisher.Mono;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public Mono<ErrorResponse> businessExceptionHandler(BusinessException e) {
        return Mono.fromSupplier(
                () -> ErrorResponse.builder(e, e.getStatus(), e.getMessage())
                        .build()
        );
    }

    @ExceptionHandler({
            ServerWebInputException.class,
            ResponseStatusException.class
    })
    public Mono<ErrorResponse> bindExceptionHandler(Exception e) {
        return Mono.fromSupplier(
                () -> ErrorResponse.builder(e, NOT_FOUND, e.getMessage())
                        .build()
        );
    }

    @ExceptionHandler(Exception.class)
    public Mono<ErrorResponse> exceptionHandler(Exception e) {
        return Mono.fromSupplier(
                () -> ErrorResponse.builder(e, INTERNAL_SERVER_ERROR, e.getMessage())
                        .build()
        );
    }
}
