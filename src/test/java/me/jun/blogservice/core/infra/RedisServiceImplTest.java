package me.jun.blogservice.core.infra;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import redis.embedded.RedisServer;

import static me.jun.blogservice.support.RedisFixture.REDIS_PORT;

@ActiveProfiles("test")
@SpringBootTest(properties = "spring.cloud.config.enabled=false")
class RedisServiceImplTest {

    @Autowired
    private RedisServiceImpl redisService;

    private RedisServer redisServer;

    @BeforeEach
    void setUp() {
        redisServer = new RedisServer(REDIS_PORT);
        redisServer.start();
    }

    @AfterEach
    void tearDown() {
        redisServer.stop();
    }

    @Test
    void deleteArticleListTest() {
        redisService.deleteArticleList();
    }
}