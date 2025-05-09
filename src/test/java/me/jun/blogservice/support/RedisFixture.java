package me.jun.blogservice.support;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
abstract public class RedisFixture {

    public static final int REDIS_PORT = 6379;

    public static final int ARTICLE_SIZE = 10;
}
