package me.jun.blogservice.core.domain.repository;

import me.jun.blogservice.core.domain.Article;
import me.jun.blogservice.core.domain.Writer;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {

    List<Article> findAllBy(Pageable pageable);

    @Modifying(clearAutomatically = true)
    @Query("delete from Article a where a.writer = :writer")
    void deleteAllByWriter(Writer writer);
}
