package me.jun.blogservice.core.presentation;

import io.micrometer.core.annotation.Timed;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.jun.blogservice.core.application.CategoryService;
import me.jun.blogservice.core.application.dto.CategoryListResponse;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static reactor.core.scheduler.Schedulers.boundedElastic;

@Slf4j
@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping(
            value = "/query",
            produces = APPLICATION_JSON_VALUE
    )
    @Timed(
            value = "categories.retrieveList",
            longTask = true
    )
    public Mono<ResponseEntity<CategoryListResponse>> retrieveCategoryList(
            @RequestParam("page") int page,
            @RequestParam("size") int size
    ) {
        Mono<PageRequest> requestMono = Mono.fromSupplier(
                        () -> PageRequest.of(page, size)
                ).log()
                .publishOn(boundedElastic()).log();

        return categoryService.retrieveCategoryList(requestMono).log()
                .map(response -> ResponseEntity.ok()
                        .body(response)
                ).log()
                .doOnError(throwable -> log.error(throwable.getMessage()));
    }
}
