package me.jun.blogservice.core.domain;

import me.jun.blogservice.core.domain.exception.WriterMismatchException;
import org.junit.jupiter.api.Test;

import static me.jun.blogservice.support.WriterFixture.WRITER_ID;
import static me.jun.blogservice.support.WriterFixture.writer;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SuppressWarnings("deprecation")
class WriterTest {

    @Test
    void constructorTest() {
        Writer expected = new Writer();

        assertThat(new Writer())
                .isEqualToComparingFieldByField(expected);
    }

    @Test
    void constructorTest2() {
        Writer expected = Writer.builder()
                .value(WRITER_ID)
                .build();

        assertThat(writer())
                .isEqualToComparingFieldByField(expected);
    }

    @Test
    void validateTest() {
        assertDoesNotThrow(
                () -> writer().validate(WRITER_ID)
        );
    }

    @Test
    void validateFailTest() {
        assertThrows(
                WriterMismatchException.class,
                () -> writer().validate(2L)
        );
    }
}