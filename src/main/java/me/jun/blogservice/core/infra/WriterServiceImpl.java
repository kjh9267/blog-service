package me.jun.blogservice.core.infra;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;
import me.jun.blogservice.core.application.WriterService;
import me.jun.blogservice.core.application.dto.RetrieveWriterIdRequest;
import me.jun.blogservice.core.application.dto.WriterResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Slf4j
@Component
public class WriterServiceImpl implements WriterService {

    private final WebClient.Builder writerWebClientBuilder;

    private final String writerUri;

    public WriterServiceImpl(
            WebClient.Builder writerWebClientBuilder,
            @Value("#{${writer-uri}}") String writerUri
    ) {
        this.writerWebClientBuilder = writerWebClientBuilder;
        this.writerUri = writerUri;
    }

    @Override
    @CircuitBreaker(name = "writerCircuitBreaker")
    public Mono<Object> retrieveWriterIdByEmail(String email) {
        RetrieveWriterIdRequest request = RetrieveWriterIdRequest.builder()
                .email(email)
                .build();

        return writerWebClientBuilder.build()
                .post()
                .uri(writerUri)
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON)
                .body(Mono.just(request), RetrieveWriterIdRequest.class)
                .retrieve()
                .bodyToMono(WriterResponse.class).log()
                .map(writer -> writer.getId()).log()
                .map(id -> (Object) id);
    }
}
