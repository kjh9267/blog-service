package me.jun.blogservice.core.application;

import reactor.core.publisher.Mono;

public interface WriterService {

    Mono<Object> retrieveWriterIdByEmail(String email);
}
