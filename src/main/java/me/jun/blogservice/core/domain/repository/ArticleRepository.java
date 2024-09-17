package me.jun.blogservice.core.domain.repository;

import me.jun.blogservice.core.domain.Article;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {

    List<Article> findAllBy(Pageable pageable);
}
