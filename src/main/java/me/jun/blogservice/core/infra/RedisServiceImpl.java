package me.jun.blogservice.core.infra;

import lombok.RequiredArgsConstructor;
import me.jun.blogservice.core.application.RedisService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.core.ReactiveValueOperations;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RedisServiceImpl implements RedisService {

    private final ReactiveRedisTemplate<String, String> redisTemplate;

    @Value("${article-page}")
    private int articlePage;

    @Value("${article-size}")
    private int articleSize;

    @Override
    public void deleteArticleList() {
        ReactiveValueOperations<String, String> operations = redisTemplate.opsForValue();
        operations.delete(String.format("articles:%d:%d", articlePage, articleSize)).block();
    }
}
