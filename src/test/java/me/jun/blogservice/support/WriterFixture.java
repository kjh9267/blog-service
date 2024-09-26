package me.jun.blogservice.support;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import me.jun.blogservice.core.domain.Writer;

import static me.jun.blogservice.support.ArticleFixture.WRITER_ID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
abstract public class WriterFixture {

    public static final String WRITER_BASE_URL = "http://127.0.0.1";

    public static final int WRITER_BASE_URL_PORT = 8080;

    public static final String WRITER_EMAIL = "asdf@asdf.com";

    public static final String WRITER_RESPONSE_JSON = "{\"id\": 1, " +
            "\"email\": \"asdf@asdf.com\", " +
            "\"name\": \"name string\", " +
            "\"role\": \"USER\"}";

    public static final String WRITER_RESPONSE = "{id=1, email=asdf@asdf.com, name=name string, role=USER}";

    public static final Writer writer() {
        return Writer.builder()
                .value(WRITER_ID)
                .build();
    }
}