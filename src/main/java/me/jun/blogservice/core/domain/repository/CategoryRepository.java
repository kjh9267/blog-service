package me.jun.blogservice.core.domain.repository;

import me.jun.blogservice.core.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
