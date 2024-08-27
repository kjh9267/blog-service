package me.jun.blogservice.core.domain.repository;

import me.jun.blogservice.core.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {

}
