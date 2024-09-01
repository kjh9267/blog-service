package me.jun.blogservice.core.infra;

import lombok.extern.slf4j.Slf4j;
import me.jun.blogservice.core.application.WriterService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class WriterServiceImpl implements WriterService {

    private final WebClient writerWebClient;

    private final String writerUri;

    public WriterServiceImpl(WebClient writerWebClient, @Value("#{${writer-uri}}") String writerUri) {
        this.writerWebClient = writerWebClient;
        this.writerUri = writerUri;
    }

    @Override
    public Mono<Object> retrieveWriterIdByEmail(String email) {
        return writerWebClient.get()
                .uri(writerUri + "/" + email)
                .retrieve()
                .bodyToMono(Object.class)
                .log()
                .doOnError(throwable -> log.info("{}", throwable));
    }
}