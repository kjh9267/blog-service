package me.jun.blogservice.core.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.jun.blogservice.core.application.dto.CategoryResponse;
import me.jun.blogservice.core.application.dto.CreateCategoryRequest;
import me.jun.blogservice.core.application.dto.RetrieveCategoryRequest;
import me.jun.blogservice.core.application.exception.CategoryNotFoundException;
import me.jun.blogservice.core.domain.Category;
import me.jun.blogservice.core.domain.repository.CategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;


    public Mono<CategoryResponse> createCategory(Mono<CreateCategoryRequest> requestMono) {
        return requestMono.log()
                .map(request -> categoryRepository.save(
                        Category.of(request.getName()))
                )
                .map(CategoryResponse::of)
                .doOnError(throwable -> log.info("{}", throwable));
    }

    public Mono<CategoryResponse> retrieveCategory(Mono<RetrieveCategoryRequest> requestMono) {
        return requestMono.map(
                request -> categoryRepository.findById(request.getId())
                        .orElseThrow(() -> CategoryNotFoundException.of(String.valueOf(request.getId())))
        )
                .log()
                .map(CategoryResponse::of)
                .doOnError(throwable -> log.info("{}", throwable));
    }
}
